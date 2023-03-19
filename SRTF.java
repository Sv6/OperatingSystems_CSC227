

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SRTF {

    static String[] SENTENCE;

    public static void main(String args[]) throws IOException
    {

        Scanner sentence = new Scanner(new File("test_SRTF.txt"));//// change the name of the file
        ArrayList<String> sentenceList = new ArrayList<String>();
        int pid[] = new int[30]; // it takes pid of process
        int arrival_time[] = new int[30]; // at means arrival time
        int bt[] = new int[30]; // bt means burst time

        int count = -1 ;

        while (sentence.hasNextLine())//reads all lines in the file
            sentenceList.add(sentence.nextLine());// add it to the list

        String[] sentenceArray = sentenceList.toArray(new String[sentenceList.size()]);// to covert it to array

        try {

            for (int r=0;r<sentenceArray.length;r++)
            {
                SENTENCE = sentenceArray[r].split(",");
                int [] arr = new int [SENTENCE.length];
                //will convert the String to int arrray
                for(int i=0; i<SENTENCE.length; i++) {
                    arr[i] = Integer.parseInt(SENTENCE[i]);
                }

                count++;
                pid[count]=arr[0];
                arrival_time[count]=arr[1];
                bt[count]=arr[2];

            }

        }catch (java.lang.NumberFormatException e) {}


        //check what the last full element line is? 4 lines
        System.out.println("counter: "+count);
        int newarrival_time[]= new int[count+1];
        int newpid[]= new int[count+1];
        int newBT[]= new int[count+1];


        // for AT
        for(int i =0 ; i<count+1;i++) //because the size is 30 every index will be zero until the user change it
            newarrival_time[i]=arrival_time[i];

        // for PID
        for(int i =0 ; i<count+1;i++) //because the size is 30 every index will be zero until the user change it
            newpid[i]=pid[i];

        //for BT to avoid zeros
        for(int i =0 ; i<count+1;i++) //because the size is 30 every index will be zero until the user change it
            newBT[i]=bt[i];

        ////////////////////////////////////////////// here will use your calculation to find WT and CT

        int n=count+1;
        int proc[][] = new int[n][4];//proc[][0] is the AT ,	[][1] - BT,	[][2] - WT,	[][3] - TT

        for(int i = 0; i < n; i++)
        {
            proc[i][1] = newBT[i];
            proc[i][0] = newarrival_time[i];
        }
        System.out.println();


        //Calculation of Total Time and Initialization of Time Chart array

        int total_time = 0;

        for(int i = 0; i < n; i++)
        {
            total_time += proc[i][1];
        }

        int time_chart[] = new int[total_time];

        for(int i = 0; i < total_time; i++)
        {
            //Selection of shortest process which has arrived
            int sel_proc = 0;
            int min = 99999;
            for(int j = 0; j < n; j++)
            {
                if(proc[j][0] <= i)//Condition to check if Process has arrived
                {
                    if(proc[j][1] < min && proc[j][1] != 0)
                    {
                        min = proc[j][1];
                        sel_proc = j;
                    }
                }
            }

            //Assign selected process to current time in the Chart
            time_chart[i] = sel_proc;

            //Decrement Remaining Time of selected process by 1 since it has been assigned the CPU for 1 unit of time
            proc[sel_proc][1]--;

            //WT and TT Calculation
            for(int j = 0; j < n; j++)
            {
                if(proc[j][0] <= i)
                {
                    if(proc[j][1] != 0)
                    {
                        proc[j][3]++;//If process has arrived and it has not already completed execution its TT is incremented by 1
                        if(j != sel_proc)//If the process has not been currently assigned the CPU and has arrived its WT is incremented by 1
                            proc[j][2]++;
                    }
                    else if(j == sel_proc)//This is a special case in which the process has been assigned CPU and has completed its execution
                        proc[j][3]++;
                }
            }
        }
        System.out.println();

        //Printing the WT and CT for each Process
        System.out.println("P\t WT \t TT ");
        for(int i = 0; i <n; i++)
        {
            System.out.printf("%d\t%2dms\t%2dms",i+1,proc[i][2],proc[i][3]);
            System.out.println();
        }

        System.out.println();

        // Calculate the average WT and CT
        double WT = 0,CT = 0;
        for(int i = 0; i < n; i++)
        {
            WT += proc[i][2];
            CT += proc[i][3];
        }
        WT /= n;
        CT /= n;
        System.out.println("The Average Waiting Time is: " + WT + "ms");
        System.out.println("The Average Copletion Time is: " + CT + "ms");
        System.out.println();
        /////////////Done, every thing is good >>>>> understand the code <<<<<
