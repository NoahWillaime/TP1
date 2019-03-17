import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class read {
    private String name;

    public read(String file){
        name = file;
    }

    public void lirefichierGris(){
        try {
            InputStream flux = new FileInputStream(name);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String line;
            String[] names = name.split("\\.");
            File fgris= new File(names[0]+".pgm");
            fgris.createNewFile();
            FileWriter fg = new FileWriter(fgris);
            int j = 0;
            int gray = 0;
            int col = 0;
            fg.write("P2");
            while ((line = buff.readLine()) != null){
                if (j > 0 && j < 4){
                    fg.write(line+"\n");
                }
                if (j >= 4){
                    if (col == 0){
                        gray += 0.299f * Integer.parseInt(line);
                    } else if (col == 1){
                        gray += 0.587f * Integer.parseInt(line);
                    } else if (col == 2){
                        gray += 0.114f * Integer.parseInt(line);
                    }
                    col++;
                    if (col == 3){
                        fg.write(gray+"\n");
                        gray = 0;
                        col = 0;
                    }
                }
                j++;
            }
            fg.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lirefichier(){
        try {
            InputStream flux = new FileInputStream(name);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String line;

            File frouge = new File("rouge_"+name);
            frouge.createNewFile();
            File fbleu = new File("bleu_"+name);
            fbleu.createNewFile();
            File fvert = new File("vert_"+name);
            fvert.createNewFile();
            FileWriter fr = new FileWriter(frouge);
            FileWriter fb = new FileWriter(fbleu);
            FileWriter fv = new FileWriter(fvert);
            int j = 0;
            int col = 0;
            while ((line = buff.readLine()) != null){
                if (j < 4){
                    fr.write(line+"\n");
                    fv.write(line+"\n");
                    fb.write(line+"\n");
                }
                if (j >= 4){
                    if (col == 0){
                        fr.write(line+"\n");
                        fr.write("0\n");
                        fr.write("0\n");
                    } else if (col == 1){
                        fv.write("0\n");
                        fv.write(line+"\n");
                        fv.write("0\n");
                    } else if (col == 2){
                        fb.write("0\n");
                        fb.write("0\n");
                        fb.write(line+"\n");
                    }
                    col++;
                    if (col == 3)
                        col = 0;
                }
                j++;
            }
            fr.close();
            fv.close();
            fb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cropImg(){
        try {
            InputStream flux = new FileInputStream(name);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String line;
            int k = 0;
            int y = 0;
            int col = 0;
            File fcrop= new File("crop_"+name);
            fcrop.createNewFile();
            FileWriter fc = new FileWriter(fcrop);
            fc.write(buff.readLine()+"\n");
            fc.write(buff.readLine()+"\n");
            line = buff.readLine();
            String[] sizes = line.split(" ");
            int longueur = Integer.parseInt(sizes[0]);
            int hauteur= Integer.parseInt(sizes[1]);
            fc.write(longueur/2+" "+hauteur/2+"\n");
            fc.write(buff.readLine()+"\n");
            int[][] tabR = new int[hauteur][longueur];
            int[][] tabG = new int[hauteur][longueur];
            int[][] tabB = new int[hauteur][longueur];
            while ((line = buff.readLine()) != null){
                if (k >= longueur){
                    k = 0;
                    y++;
                }
                if (col < 3){
                    if (col == 0)
                        tabR[y][k] = Integer.parseInt(line);
                    else if (col == 1)
                        tabG[y][k] = Integer.parseInt(line);
                    else if (col == 2)
                        tabB[y][k] = Integer.parseInt(line);
                }
                col++;
                if (col == 3) {
                    col = 0;
                    k++;
                }
            }
            int mR = 0;
            int mG;
            int mB;
            int i;
            int j = 0;
            while (j+1 < hauteur) {
                i = 0;
                while (i+1 < longueur) {
                    mR = tabR[j][i];
                    mR += tabR[j][i+1];
                    mR += tabR[j+1][i];
                    mR += tabR[j+1][i+1];

                    mG = tabG[j][i];
                    mG += tabG[j][i+1];
                    mG += tabG[j+1][i];
                    mG += tabG[j+1][i+1];

                    mB = tabB[j][i];
                    mB += tabB[j][i+1];
                    mB += tabB[j+1][i];
                    mB += tabB[j+1][i+1];
                    //
                    fc.write(mR/4+"\n");
                    fc.write(mG/4+"\n");
                    fc.write(mB/4+"\n");
                    i += 2;
                }
                j += 2;
            }
            fc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Nom du fichier ? :");
        String name = input.next();
        read test = new read(name);
    //    test.lirefichier();
    //    test.lirefichierGris();
        test.cropImg();
    }
}
