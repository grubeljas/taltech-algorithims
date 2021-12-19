package ee.ttu.algoritmid.bond;

import java.awt.List;
import java.util.HashMap;
import java.util.LinkedList;

public class AL07 {

    public enum Network {
        FRIENDLY, UNFRIENDLY, UNKNOWN;
    }

    public HashMap<String, Network> networks = new HashMap();

    private DisjointSubsets disjointSubsets = new DisjointSubsets();

    public AL07() {
        // don't remove
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        if (!disjointSubsets.parent.containsKey(name1)) {
            addPerson(name1);
        }
        if (!disjointSubsets.parent.containsKey(name2)) {
            addPerson(name2);
        }
        if (!networks.get(name1).equals(Network.UNKNOWN) ||
                !networks.get(disjointSubsets.find(name1)).equals(Network.UNKNOWN)) {
            Network network = networks.get(disjointSubsets.find(name1));
            networks.put(name2, network);
            networks.put(disjointSubsets.find(name2), network);
        } else if (!networks.get(name2).equals(Network.UNKNOWN) ||
                !networks.get(disjointSubsets.find(name2)).equals(Network.UNKNOWN)) {
            Network network = networks.get(disjointSubsets.find(name2));
            networks.put(name1, network);
            networks.put(disjointSubsets.find(name1), network);
        }
        disjointSubsets.union(name1, name2);
    }

    public void addPerson(String name) {
        disjointSubsets.addSubset(name);
        networks.put(name, Network.UNKNOWN);
    }

    public void friendly(String name) {
        networks.put(name, Network.FRIENDLY);
        networks.put(disjointSubsets.find(name), Network.FRIENDLY);
    }

    public void unfriendly(String name) {
        networks.put(name, Network.UNFRIENDLY);
        networks.put(disjointSubsets.find(name), Network.UNFRIENDLY);
    }

    public Network memberOfNetwork(String name) {
        return networks.get(disjointSubsets.find(name));
    }

    public static void main(String[] args) {
        AL07 al07 = new AL07();
        al07.addPerson("1");
        al07.addPerson("11");
        al07.talkedToEachOther("1", "11");
        al07.addPerson("2");
        al07.addPerson("22");
        al07.unfriendly("2");
        al07.talkedToEachOther("2", "22");
        al07.talkedToEachOther("1", "2");
        System.out.println(al07.networks);
        System.out.println(al07.memberOfNetwork("11"));
    }

}