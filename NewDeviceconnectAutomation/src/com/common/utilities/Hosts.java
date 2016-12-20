package com.common.utilities;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hosts {

    public static final Map<String, String> HOSTS_PATHS = new HashMap<String, String>(19);

    static {
        HOSTS_PATHS.put("AIX", "unknown");
        HOSTS_PATHS.put("Digital Unix", "/etc/hosts");
        HOSTS_PATHS.put("FreeBSD", "/etc/hosts");
        HOSTS_PATHS.put("HP UX", "/etc/hosts");
        HOSTS_PATHS.put("Irix", "unknown");
        HOSTS_PATHS.put("Linux", "/etc/hosts");
        HOSTS_PATHS.put("Mac OS", "unknown");
        HOSTS_PATHS.put("Mac OS X", "/etc/hosts");
        HOSTS_PATHS.put("MPE/iX", "unknown");
        HOSTS_PATHS.put("Netware 4.11", "unknown");
        HOSTS_PATHS.put("OS/2", "unknown");
        HOSTS_PATHS.put("Solaris", "/etc/hosts");
        HOSTS_PATHS.put("Windows 2000", System.getenv("windir") + "\\system32\\drivers\\etc\\hosts");
        HOSTS_PATHS.put("Windows 7", System.getenv("windir") + "\\system32\\drivers\\etc\\hosts");
        HOSTS_PATHS.put("Windows 95", System.getenv("windir") + "\\hosts");
        HOSTS_PATHS.put("Windows 98", System.getenv("windir") + "\\hosts");
        HOSTS_PATHS.put("Windows NT", System.getenv("windir") + "\\system32\\drivers\\etc\\hosts");
        HOSTS_PATHS.put("Windows Vista", System.getenv("windir") + "\\system32\\drivers\\etc\\hosts");
        HOSTS_PATHS.put("Windows XP", System.getenv("windir") + "\\system32\\drivers\\etc\\hosts");
    }
    private File hosts = null;
    private String hostsPath = "";
    private Map<String, String> entries = null;
    private List<String> hostOtherEntries = new ArrayList<String>();

    public Hosts() throws Exception {
        String osName = System.getProperty("os.name");
        hostsPath = HOSTS_PATHS.get(osName);

        if (hostsPath.equals("unknown") || hostsPath.equals("") || hostsPath == null) {
            throw new Exception("OS is unsupported. If you would like to set the"
                    + "hosts path manually, use Hosts.setHostsPath(String)");
        }

        hosts = new File(hostsPath);

        if (!hosts.exists()) {
            throw new Exception("Unable to find hosts file at specified path."
                    + " Please set hostsPath manually with .setHostsPath()");
        }
    }

    /**
     * Collects the entries in the OS's hosts file and parses them into a map.
     * Commeants are held in a seperate Array.
     * @return Map of entries
     * @throws Exception if does not have right to read
     */
    public Map<String, String> parse() throws Exception {
        entries = new LinkedHashMap<String, String>();

        try {
            if (!hosts.canRead()) {
                throw new Exception("Do not have permissiont to read file.");
            }

            Scanner in = new Scanner(hosts);
            String crntLine = "";
            String[] split;

            while (in.hasNextLine()) {
                crntLine = in.nextLine();

                /* All needed info is to the left of the comment # if there is one.
                 * So this checks for a comment and grabs everything to the left
                 * of it.
                 */
                if (crntLine.contains("#")) {
                    split = crntLine.split("#");
                    if (split.length > 0) {
                        if (split[0].length() > 0) {
                            crntLine = split[0] = split[0].trim();
                        } else {
                            crntLine = "";
                        }
                        /* Adds commment line to Comment list */
                        if (split[1].length() > 0) {
                            hostOtherEntries.add("#" + split[1]);
                        } else {
                            hostOtherEntries.add("#");
                        }
                    } else {
                        crntLine = "";
                    }
                }

                /* Checks the remaining text, which is gaurenteed not to
                 * have a comment at this point, to see if there is a delimiter
                 * so that two possible valuebles can be extracted.
                 */
                if (crntLine.contains("\t")) {
                    split = crntLine.split("\t");
                    for (String str : split) {
                        str = str.trim();
                    }
                    if (split[0].contains("::")) {
                        hostOtherEntries.add(split[0] + "\t" + split[1]);
                    } else {
                        entries.put(split[1], split[0]);
                    }
                } else {
                    if (crntLine.contains(" ")) {
                        split = crntLine.split(" ");
                        for (String str : split) {
                            str = str.trim();
                        }

                        if (split[0].contains("::")) {
                            hostOtherEntries.add(split[0] + "\t" + split[1]);
                        } else {
                            entries.put(split[1], split[0]);
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hosts.class.getName()).log(Level.SEVERE, null, ex);
        }

        return entries;
    }

    /**
     * Adds an entry into the programs representation of the 'hosts' file.
     * @param redirect The URL that will point to the target
     * @param target The server that redirect is to point to
     * @return true if successful, false if not
     * @throws UnknownHostException 
     */
    public boolean add(String redirect, String target) throws UnknownHostException {
        if (entries == null) {
            return false;
        }

        InetAddress targetAddr = Inet4Address.getByName(target);
        entries.put(redirect, targetAddr.getHostAddress());

        return true;
    }

    /**
     * Removes an entry from the program representation of the 'hosts' file.
     * @param redirect the redirect URL of the entry that is to be removed
     * @return true if successful
     */
    public boolean remove(String redirect) {
        if (entries == null) {
            return false;
        }

        entries.remove(redirect);

        return true;
    }

    /**
     * Lists all entries in the programs representation of the 'hosts' file.
     * @return a String containing a list of all entries
     */
    public String printList() {
        if (entries == null) {
            return "Failed to parse hosts file.";
        }

        String listString = "";

        Object[] keys = entries.keySet().toArray();
        for (int i = 0; i < entries.size(); i++) {
            String key = (String) keys[i];
            listString += i + ")\t" + (String) entries.get(key) + "\t" + key + "\n";
        }

        return listString;
    }

    /**
     * Clears the 'hosts' file and replaces it with the entries that are in
     * the programs representation of it.
     * @throws Exception 
     */
    public void persist() throws Exception {
        if (!hosts.canWrite()) {
            throw new Exception("Does not have permission to write.");
        }

        /* Clears the hosts file for re-population */
        FileOutputStream erasor=null;
        try {
        erasor = new FileOutputStream(hosts);
        
            erasor.write((new String()).getBytes());
            erasor.flush();
        } 
        catch(Exception e)
        {
        	e.getMessage();
        }
        finally {
            erasor.close();
        }

        Writer out = new BufferedWriter(new PrintWriter(hosts));
        try {
            for (String comment : hostOtherEntries) {
                out.write(comment + "\n");
            }

            out.write("\n");

            Set<String> keys = entries.keySet();
            for (String key : keys) {
                out.write(entries.get(key) + "\t" + key + "\n");
            }

            out.flush();
        } finally {
            out.close();
        }
    }

    public static String printHelp() {
        String help = "";
        help += "\nHosts [command] [URL-to-redirect] [URL/IP-target]\n\n";
        help += " URL-to-redirect \t The URL that you wish to override the DNS entry of.\n";
        help += " URL/IP-target \t\t Where you want the other URL to redirect to.\n";
        help += " --list -l /L \t\t Lists all entries in the hosts file.\n";
        help += " --add -a /ADD \t\t Adds entry into the host file. "
                + "If the redirect URL\n\t\t\t already exists, it is over written.\n";
        help += " --remove -r /REM \t Removes entry from hosts file. Only requires "
                + "the\n\t\t\t redirect URL to be specified. The index of the entry\n\t\t\t in "
                + "hosts file can be specified instead.\n";
        help += " --help -? /? \t\t Shows this message.\n";
        help += " --info -i /I \t\t Display software information.\n";
        help += "\nExample: \"Hosts --add goo.gl google.com\"\n"
                + "This allows you to access google from goo.gl address.\n";
        help += "Example: \"Hosts --remove goo.gl\"\n"
                + "Removes goo.gl from the Hosts file.\n\n";
        help += "Note: if you use a 'URL-to-redirect' as a target in a different entry,"
                + "The URL that redirects to it will redirect to the target's target:"
                + "If google.com redirects to msn.com and msn.com redirects to "
                + "yahoo.com, then google.com redirects to yahoo.com.\n";

        return help;
    }

    private static void executeList() throws Exception {
        Hosts hosts = new Hosts();
        hosts.parse();
        System.out.println(hosts.printList());
    }

    private static void executeAdd(String redirect, String target) throws Exception {
        Hosts hosts = new Hosts();
        hosts.parse();
        if (hosts.add(redirect, target)) {
            hosts.persist();
        } else {
            System.out.println("Failed to add.");
        }
    }

    private static void executeRemove(String redirect) throws Exception {
        Hosts hosts = new Hosts();
        hosts.parse();
        if (hosts.remove(redirect)) {
            hosts.persist();
        } else {
            System.out.println("Failed to remove.");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length <= 0) {
            System.out.println(printHelp());
            return;
        }
        if (args.length >= 1) {
            /* Handles help command */
            if (args[0].equals("--help") || args[0].equals("-?") || args[0].equals("/?")) {
                System.out.println(printHelp());
                return;
            }
            /* Handles List command */
            if (args[0].equals("--list") || args[0].equals("-l")
                    || args[0].equals("/L")) {
                Hosts.executeList();
                return;
            }
            /* Handles info */
            if (args[0].equals("--info") || args[0].equals("-i") || args[0].equals("/I")) {
                System.out.println("Hosts version: .1");
                System.out.println("Copyright 2011 Michael Archibald");
                return;
            }
            /* Handles add */
            if (args[0].equals("--add") || args[0].equals("-a") || args[0].equals("/ADD")) {
                if (args.length >= 3) {
                    Hosts.executeAdd(args[1], args[2]);
                    return;
                } else {
                    System.out.println("Must specify a redirect URL and a target server.");
                    return;
                }
            }
            if (args[0].equals("--remove") || args[0].equals("-r") || args[0].equals("/R")) {
                if (args.length >= 2) {
                    executeRemove(args[1]);
                } else {
                    System.out.println("Must specify the redirect URL of the entry you wish to remove.");
                }
                return;
            }
        }
    }
}