package seventeenth.group.effect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.util.Hashtable;

public class CacheDataLoader {

    private static CacheDataLoader instance;

    private String frameFile = "data/frame.txt";
    private String animationFile = "data/animation.txt";
    private String physmapfile = "data/map/physicMap.txt";
    private String backgroundmapfile = "data/map/backgroundMap.txt";

    private Hashtable<String, FrameImage> frameImages;
    private Hashtable<String, Animation> animations;
    private int[][] phys_map;
    private int [][] background_map;

    private CacheDataLoader() {}

    public static CacheDataLoader getInstance() {
        if(instance == null) instance = new CacheDataLoader();
        return instance;
    }

    public void LoadData() throws IOException {
        LoadFrame();
        LoadAnimation();
        LoadPhysMap();
        LoadBackgroundMap();
    }

    public int[][] getPhysicalMap() {
        return instance.phys_map;
    }

    public void LoadPhysMap() throws IOException {
        FileReader fr = new FileReader(physmapfile);
        BufferedReader br =  new BufferedReader(fr);

        String line = null;

        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);

        instance.phys_map = new int[numberOfRows][numberOfColumns];

        for(int i = 0; i < numberOfRows; i++) {
            line = br.readLine();
            String[] str = line.split(" ");
            for(int j = 0; j < numberOfColumns; j++) {
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
            }
        }

        /*for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                System.out.print(" " + instance.phys_map[i][j]);
            }
            System.out.println();
        }*/

        br.close();
    }

    public int [][] getBackgroundMap (){
        return instance.background_map;
    }

    public void LoadBackgroundMap() throws IOException {
        FileReader fr  = new FileReader(backgroundmapfile);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
        instance.background_map = new int [numberOfRows][numberOfColumns];
        for (int i=0; i<numberOfRows; i++){
            line = br.readLine();
            String [] str = line.split(" ");
            for (int j=0; j < numberOfColumns; j++){
                instance.background_map[i][j] = Integer.parseInt(str[j]);
            }
        }
        /*for (int i = 0; i< numberOfRows; i++){
            for (int j=0; j<numberOfColumns; j++){
            System.out.print(" " + instance.background_map[i][j]);
            }
            System.out.println();
        }*/
        br.close();
    }

    public void LoadFrame() throws IOException {
        frameImages = new Hashtable<String, FrameImage>();

        FileReader fr = new FileReader(frameFile);
        BufferedReader br = new BufferedReader(fr); // bo doc

        String line = null;  // dung de doc tung dong trong file

        if(br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        }
        else {

            // dua con tro doc file ve dau file
            fr = new FileReader(frameFile);
            br = new BufferedReader(fr);

            while((line = br.readLine()).equals(""));

            int n = Integer.parseInt(line);

            for(int i = 0; i < n; i++) {

                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                frame.setName(line);

                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                String path = str[1];

                BufferedImage image = ImageIO.read(new File(path));
                frame.setImage(image);
                instance.frameImages.put(frame.getName(), frame);
            }
        }
        br.close();
    }

    public FrameImage getFrameImage(String name) {
        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;
    }

    public void LoadAnimation() throws IOException {
        animations = new Hashtable<String, Animation>();

        FileReader fr = new FileReader(animationFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        if(br.readLine() == null) {
            System.out.println("No data");
            throw new IOException();
        }
        else {
            fr = new FileReader(animationFile);
            br = new BufferedReader(fr);

            while((line = br.readLine()).equals(""));

            int n = Integer.parseInt(line);

            for(int i = 0; i < n; i++) {

                Animation animation = new Animation();

                while((line = br.readLine()).equals(""));
                animation.setName(line);

                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");

                for(int j = 0; j < str.length; j += 2) {
                    animation.addFrame(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));
                }
                instance.animations.put(animation.getName(), animation);
            }
        }
        br.close();
    }

    public Animation getAnimation(String name) {
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }
}
