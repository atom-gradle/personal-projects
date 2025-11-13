package neuralnetwork;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.util.function.*;

import javax.imageio.*;
import java.awt.image.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static double[][][] readImage(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()) {
            throw new FileNotFoundException("File " + filePath + " does not exists.");
        }

        BufferedImage bufferedImage = ImageIO.read(file);
        final int width = bufferedImage.getWidth(),height = bufferedImage.getHeight();
        System.out.printf("Input Image Width:%d, Height:%d \n",width,height);

        double[][][] output = new double[1][width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bufferedImage.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                output[0][y][x] = calcAbsolutePixelAvg(red,blue,green);
            }
        }

        // 归一化到 [0, 1] 范围
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[0].length; j++) {
                for (int k = 0; k < output[0][0].length; k++) {
                    output[i][j][k] /= 255.0;
                }
            }
        }

        return output;
    }

    public static int getTargetFromFileName(String filePath) {
        Pattern pattern = Pattern.compile("-\\d+\\.");
        Matcher matches = pattern.matcher(filePath);
        String result = null;
        while(matches.find()) {
            result = matches.group();
        }
        return Integer.parseInt(result.substring(1,2));
    }

    public static int getPredictedValueFromArray(double[] array) {
        int maxIndex = 0;
        for(int i = 0;i < array.length;i++) {
            if(array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static double calcAbsolutePixelAvg(int red,int blue,int green) {
        return (double) (red + blue + green) / 3;
    }

    private static double calcHumanoidPixelAvg(int red,int blue,int green) {
        return 0.299 * red + 0.587 * green + 0.114 * blue;
    }

    private static double calcSimpleAvg(int red,int blue,int green) {
        return ((red + blue + green) / 3) > 127 ? 0.0d : 1.1d;
    }

    public static void printArray(double[][][] array) {
        int depth = array.length,width = array[0].length,height = array[0][0].length;
        for(int k = 0;k < depth;k++) {
            for(int i = 0;i < height;i++) {
                for(int j = 0;j < width;j++) {
                    System.out.print(array[k][i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printArray(double[] array) {
        for(int i = 0;i < array.length;i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void printArray(double[][] array) {
        for(int i = 0;i < array.length;i++) {
            for(int j = 0;j < array[0].length;j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double[] getLabelArray(int target) {
        double[] result = new double[10];
        Arrays.fill(result,0.0d);
        result[target] = 1.0d;
        return result;
    }

}
