import java.io.File;
import java.util.*;

public class PriorityNonPreemptive {

    public static void main(String[] args) throws Exception {

        System.out.println("------------------------------------\n \t \tPriority Scheduling \n------------------------------------");

        Scanner reader = new Scanner(new File("testdata.txt")); //CHECK THE NAME

        ArrayList<String> proc = new ArrayList<>();  // ID
        ArrayList<Integer> bt = new ArrayList<>(); // Burst
        ArrayList<Integer> prior = new ArrayList<>();  // Priority
        int nbP = 0;  // size

        System.out.println();
// ==================================    Listing Sentences     ==================================
        ArrayList<String> sentence = new ArrayList<String>();
        while (reader.hasNextLine()) {
            sentence.add(reader.nextLine());
        }
        int length = sentence.size();
        reader.close();
// ==================================    Listing Numbers     ==================================
        ArrayList<String> nums;
        ArrayList<ArrayList> nSentence = new ArrayList<ArrayList>();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(sentence.get(i).charAt(0))) {
                nums = new ArrayList(List.of(sentence.get(i).replace(" ", "").split(",")));
                nSentence.add(nums);
            }
        }
// ==================================    ID     ==================================
        for (int i = 0; i < length; i++) {
            if (sentence.get(i).startsWith("Job")) {
                proc.add(sentence.get(i));
                nbP++;
            }
        }
        String[] process = proc.toArray(new String[0]);
// ==================================    Bursts     ==================================
        for (int i = 0; i < nbP; i++)
            bt.add(Integer.parseInt((String) nSentence.get(i).get(0)));
        Integer[] BT = bt.toArray(new Integer[0]);
// ==================================    PRIORITY     ==================================
        for (int i = 0; i < nbP; i++)
            prior.add(Integer.parseInt((String) nSentence.get(i).get(1)));
        Integer[] priority = prior.toArray(new Integer[0]);
// ==================================    SORTING     ==================================
            int temp;
            for (int i = 0; i < nbP - 1; i++)
                for(int j = 0; j < nbP - 1; j++)
                    if(priority[j] > priority[j+1]) {
                        temp = priority[j];
                        priority[j] = priority[j+1];
                        priority[j+1] = temp;

                        temp = BT[j];
                        BT[j] = BT[j+1];
                        BT[j+1] = temp;

                        String temp2 = process[j];
                        process[j] = process[j + 1];
                        process[j + 1] = temp2;
                    }
// ==================================    Total Around Time & Waiting Time     ==================================
        int TAT[] = new int[nbP];  // Turn Around Time
        int WT[] = new int[nbP]; // Waiting Time
            for(int i = 0; i < nbP; i++) {
                TAT[i] = BT[i] + WT[i];
                try {
                    WT[i + 1] = TAT[i];
                }catch (IndexOutOfBoundsException e) {
                    break;
                }
            }

            double totalWT = 0 , avgWT;
            double totalTAT = 0, avgTAT;


// ==================================    Chart & Average     ==================================
            System.out.println("============================================================");
            System.out.println("Process_ID \t Burst_Time \t Waiting_Time \t Total_Around_Time");
            for(int i = 0; i < nbP; i++) {
                System.out.println(process[i] + "         " + BT[i] + "ms              " + WT[i] + "ms                " + TAT[i] + "ms");
                totalWT += WT[i] * 1.0;
                totalTAT += (WT[i] + BT[i]) * 1.0;
            }
            avgWT = totalWT / nbP;
            avgTAT = totalTAT / nbP;
            System.out.println("============================================================");
            System.out.println("Average Waiting Time : " + String.format("%.2f",avgWT) + "ms");
            System.out.println("Average Total Around Time: " + String.format("%.2f",avgTAT) + "ms");

        }
    }

