package seventeenth.group.effect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CacheDataLoader {

    private static CacheDataLoader instance;

    private String frameFile = "data/frame.txt";
    private String animationFile = "data/animation.txt";
    private String physmapfile = "data/map/physicMap.txt";
    private String backgroundmapfile = "data/map/backgroundMap.txt";

    private Map<String, FrameImage> frameImages;
    private Map<String, Animation> animations;
    private Map<String, BufferedImage> imageCache = new HashMap<>();

    private int[][] phys_map;
    private int[][] background_map;

    private CacheDataLoader() {}

    public static CacheDataLoader getInstance() {
        if(instance == null) instance = new CacheDataLoader();
        return instance;
    }
    public BufferedImage loadImage(String path) throws IOException {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path); // Trả về ảnh đã được cache
        }

        BufferedImage image = ImageIO.read(new File(path));
        imageCache.put(path, image); // Lưu vào cache
        return image;
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
        frameImages = new HashMap<>(); // Sử dụng HashMap thay vì Hashtable

        FileReader fr = new FileReader(frameFile);
        BufferedReader br = new BufferedReader(fr);

        String line = null;
        if ((line = br.readLine()) == null) {
            System.out.println("No data in frame file.");
            throw new IOException("Frame file is empty or not found.");
        }

        // Tiếp tục xử lý các dòng khác
        while ((line = br.readLine()).equals(""));
        int n = Integer.parseInt(line);
        for (int i = 0; i < n; i++) {
            FrameImage frame = new FrameImage();
            while ((line = br.readLine()).equals(""));
            frame.setName(line);
            while ((line = br.readLine()).equals(""));
            String[] str = line.split(" ");
            String path = str[1];

            BufferedImage image = loadImage(path); // Sử dụng loadImage từ bộ nhớ cache
            frame.setImage(image);
            frameImages.put(frame.getName(), frame);
        }
        br.close();
    }


    public FrameImage getFrameImage(String name) {
        if (frameImages == null || !frameImages.containsKey(name)) {
            System.out.println("FrameImage '" + name + "' not found or frameImages not loaded.");
            return null; // Trả về null nếu dữ liệu không tồn tại
        }
        //FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        //return frameImage;
        return frameImages.get(name);
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
        if (animations == null || !animations.containsKey(name)) {
            System.out.println("Animation '" + name + "' not found or animations not loaded.");
            return null; // Trả về null nếu dữ liệu không tồn tại
        }
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
    }
}
