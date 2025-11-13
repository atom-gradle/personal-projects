package neuralnetwork;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MainWindow extends JFrame {

    private JPanel mainPanel;
    private JTextField trainPathField;
    private JTextField testPathField;
    private DrawingCanvas drawingCanvas;
    private JButton trainButton;
    private JButton testModelButton;
    private JButton testButton;
    private JLabel resultLabel;
    private JLabel accuracyLabel;
    private static final Font commonFont = new Font("微软雅黑",Font.PLAIN,16);


    private NeuralNetwork network;
    private boolean isTrained;
    private NetworkHelper networkHelper;
    public MainWindow() {
        init();
        initNetwork();
        networkHelper = new NetworkHelper();
    }

    private void init() {
        // 设置Windows系统样式
        setWindowsLookAndFeel();

        setTitle("CNN手写数字识别");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 800);
        setLocationRelativeTo(null); // 窗口居中

        createUI();

        setVisible(true);
    }

    private void initNetwork() {
        network = new NeuralNetwork();
        network.init();
    }

    private void setWindowsLookAndFeel() {
        try {
            // 设置Windows系统样式
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Get System UI Look And Fell failed");
            e.printStackTrace();
        }
    }

    private void createUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 顶部：文件路径选择区域
        mainPanel.add(createFileSelectionPanel(), BorderLayout.NORTH);

        // 中间：绘图区域
        mainPanel.add(createDrawingPanel(), BorderLayout.CENTER);

        // 底部：按钮和结果显示区域
        mainPanel.add(createControlPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFileSelectionPanel() {
        JPanel filePanel = new JPanel(new GridLayout(2, 3, 5, 5));

        // 训练集路径选择
        JLabel train_set_path_label = new JLabel("训练集路径:");
        train_set_path_label.setFont(commonFont);
        filePanel.add(train_set_path_label);
        trainPathField = new JTextField();
        trainPathField.setFont(commonFont);
        filePanel.add(trainPathField);
        JButton trainBrowseButton = new JButton("选择");
        trainBrowseButton.setFont(commonFont);
        trainBrowseButton.addActionListener(e -> browseFile(trainPathField));
        filePanel.add(trainBrowseButton);

        // 测试集路径选择
        JLabel test_set_path_label = new JLabel("测试集路径:");
        test_set_path_label.setFont(commonFont);
        filePanel.add(test_set_path_label);
        testPathField = new JTextField();
        testPathField.setFont(commonFont);
        filePanel.add(testPathField);
        JButton testBrowseButton = new JButton("选择");
        testBrowseButton.setFont(commonFont);
        testBrowseButton.addActionListener(e -> browseFile(testPathField));
        filePanel.add(testBrowseButton);

        return filePanel;
    }

    private JPanel createDrawingPanel() {
        JPanel drawingPanel = new JPanel(new BorderLayout());
        drawingPanel.setBorder(BorderFactory.createTitledBorder("手写数字区域 (28x28像素网格)"));

        // 创建画布面板，添加边距使画布居中
        JPanel canvasContainer = new JPanel(new GridBagLayout());
        drawingCanvas = new DrawingCanvas();
        canvasContainer.add(drawingCanvas);

        drawingPanel.add(canvasContainer, BorderLayout.CENTER);

        // 添加 清除画布 按钮
        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("清除画布");
        clearButton.setFont(commonFont);
        clearButton.addActionListener(e -> drawingCanvas.clear());
        buttonPanel.add(clearButton);
        drawingPanel.add(buttonPanel, BorderLayout.SOUTH);

        return drawingPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        trainButton = new JButton("训练模型");
        testModelButton = new JButton("测试模型");
        testButton = new JButton("识别数字");
        trainButton.setFont(commonFont);
        testModelButton.setFont(commonFont);
        testButton.setFont(commonFont);

        // 设置按钮尺寸一致
        Dimension buttonSize = new Dimension(100, 30);
        trainButton.setPreferredSize(buttonSize);
        testModelButton.setPreferredSize(buttonSize);
        testButton.setPreferredSize(buttonSize);

        buttonPanel.add(trainButton);
        buttonPanel.add(testModelButton);
        buttonPanel.add(testButton);

        // 为按钮添加事件监听器
        trainButton.addActionListener(e -> trainModel());
        testModelButton.addActionListener(e -> testModel());
        testButton.addActionListener(e -> testDigit());

        // 结果显示面板
        JPanel resultPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        resultLabel = new JLabel("识别结果: 等待测试", JLabel.CENTER);
        accuracyLabel = new JLabel("准确率: 未训练", JLabel.CENTER);

        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        accuracyLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));

        // 设置结果标签边框
        resultLabel.setBorder(BorderFactory.createTitledBorder("识别结果"));
        accuracyLabel.setBorder(BorderFactory.createTitledBorder("模型准确率"));

        resultPanel.add(resultLabel);
        resultPanel.add(accuracyLabel);

        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(resultPanel, BorderLayout.CENTER);

        return controlPanel;
    }

    private void testModel() {
        networkHelper.testNetwork();
    }

    private void browseFile(JTextField textField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setDialogTitle("选择数据集路径");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void trainModel() {
        String trainPath = trainPathField.getText().trim();
        /*
        if (trainPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请选择训练集路径", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }
         */

        resultLabel.setText("识别结果: 训练中...");
        accuracyLabel.setText("准确率: 计算中...");

        // 禁用按钮防止重复点击
        trainButton.setEnabled(false);
        testButton.setEnabled(false);

        // 训练网络

        //TODO
        int acc_count = networkHelper.trainNetwork();
        System.out.println("accurate count: " + acc_count);
        isTrained = true;

        String accuracy = String.format("%d",(acc_count / Main.totalImageAmount));
        //SwingUtilities.invokeLater(() -> {
            accuracyLabel.setText("准确率: " + accuracy);
            trainButton.setEnabled(true);
            testButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "训练完成！", "信息", JOptionPane.INFORMATION_MESSAGE);
        //});
    }

    private void testDigit() {
        if(!isTrained) {
            JOptionPane.showMessageDialog(this, "请先训练网络", "警告", JOptionPane.WARNING_MESSAGE);
            drawingCanvas.clear();
            return;
        }

        if (drawingCanvas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先在画布上绘制一个数字", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        resultLabel.setText("识别中...");

        double[][] imageArray = drawingCanvas.getImageDataAsDoubleArray();
        int result = network.predict(imageArray);
        System.out.println("test finished");
        resultLabel.updateUI();
        resultLabel.setText("识别结果: " + result);
        resultLabel.updateUI();
    }

    // 自定义绘图画布类 - 带网格效果
    private class DrawingCanvas extends JPanel {
        private static final int CANVAS_SIZE = 280; // 280 = 28 * 10
        private static final int GRID_SIZE = 10;    // 每个格子10x10像素
        private static final int GRID_COUNT = 28;   // 28x28网格

        private BufferedImage canvas;
        private Graphics2D g2d;
        private boolean isEmpty = true;

        public DrawingCanvas() {
            setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    drawOnGrid(e.getX(), e.getY());
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    drawOnGrid(e.getX(), e.getY());
                }
            });

            initCanvas();
        }

        private void initCanvas() {
            canvas = new BufferedImage(CANVAS_SIZE, CANVAS_SIZE, BufferedImage.TYPE_INT_RGB);
            g2d = canvas.createGraphics();

            // 设置抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 填充白色背景
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

            // 绘制网格
            drawGrid();

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            isEmpty = true;
        }

        private void drawGrid() {
            g2d.setColor(Color.LIGHT_GRAY);
            // 绘制垂直线
            for (int i = 0; i <= GRID_COUNT; i++) {
                int x = i * GRID_SIZE;
                g2d.drawLine(x, 0, x, CANVAS_SIZE);
            }
            // 绘制水平线
            for (int i = 0; i <= GRID_COUNT; i++) {
                int y = i * GRID_SIZE;
                g2d.drawLine(0, y, CANVAS_SIZE, y);
            }
        }

        private void drawOnGrid(int x, int y) {
            // 转换为网格坐标
            int gridX = x / GRID_SIZE;
            int gridY = y / GRID_SIZE;

            if (gridX >= 0 && gridX < GRID_COUNT && gridY >= 0 && gridY < GRID_COUNT) {
                // 在网格中心绘制圆形
                int centerX = gridX * GRID_SIZE + GRID_SIZE / 2;
                int centerY = gridY * GRID_SIZE + GRID_SIZE / 2;
                int radius = GRID_SIZE / 2 - 1;

                g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
                repaint();
                isEmpty = false;
            }
        }

        /**
         * 获取28x28的double数组，值范围 0.0-1.0
         * 0.0表示白色(背景)，1.0表示黑色(笔画)
         * 符合MNIST数据集的格式要求
         */
        public double[][] getImageDataAsDoubleArray() {
            double[][] result = new double[28][28];

            // 采样每个10x10网格的中心区域
            for (int gridY = 0; gridY < GRID_COUNT; gridY++) {
                for (int gridX = 0; gridX < GRID_COUNT; gridX++) {
                    // 采样网格中心5x5区域的平均值
                    int startX = gridX * GRID_SIZE + GRID_SIZE / 2 - 2;
                    int startY = gridY * GRID_SIZE + GRID_SIZE / 2 - 2;
                    double sum = 0;
                    int count = 0;

                    for (int dy = 0; dy < 5; dy++) {
                        for (int dx = 0; dx < 5; dx++) {
                            int x = startX + dx;
                            int y = startY + dy;
                            if (x >= 0 && x < CANVAS_SIZE && y >= 0 && y < CANVAS_SIZE) {
                                int rgb = canvas.getRGB(x, y);
                                // 将RGB转换为灰度值(0-255)，然后归一化到0-1
                                // 注意：黑色笔画对应值接近1.0，白色背景接近0.0
                                int red = (rgb >> 16) & 0xFF;
                                int green = (rgb >> 8) & 0xFF;
                                int blue = rgb & 0xFF;
                                double gray = (red + green + blue) / 3.0 / 255.0;
                                // 反转，使黑色为1.0，白色为0.0
                                sum += (1.0 - gray);
                                count++;
                            }
                        }
                    }

                    result[gridY][gridX] = count > 0 ? sum / count : 0.0;
                }
            }

            return result;
        }

        public void clear() {
            initCanvas();
            repaint();
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public BufferedImage getImage() {
            return canvas;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvas, 0, 0, null);
        }
    }

}