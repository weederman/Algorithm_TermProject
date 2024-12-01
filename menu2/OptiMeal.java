package menu2;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class OptiMeal extends JFrame{
    private static final long serialVersionUID = 1L; // serialVersionUID 선언
    private Map<String, List<Menu>> buildings;
    private JTextField budgetField;
    private Map<Menu, JSlider> preferenceSliders;
    private JComboBox<String> dayComboBox;
    private JTextArea resultArea;

    // 색상 테마 정의
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);    
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255); 
    private static final Color ACCENT_COLOR = new Color(30, 144, 255);     
    private static final Font TITLE_FONT = new Font("맑은 고딕", Font.BOLD, 16);
    private static final Font NORMAL_FONT = new Font("맑은 고딕", Font.PLAIN, 12);

    
    private List<Menu> koreanFoodSubMenus; 
    private List<Menu> ramenSubMenus;
    
    public OptiMeal() {
        setTitle("OptiMeal - 맛있는 메뉴 추천");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(SECONDARY_COLOR);

        buildings = initializeMenus();
        preferenceSliders = new HashMap<>();

        initComponents();
        layoutComponents();

        setSize(500, 700);
        setLocationRelativeTo(null);
    }
   


    private void initComponents() {
        // 예산 입력 필드 스타일링
        budgetField = new JTextField(10);
        budgetField.setFont(NORMAL_FONT);
        budgetField.setBorder(BorderFactory.createCompoundBorder(
                budgetField.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // 요일 선택 콤보박스 스타일링
        String[] days = {"월", "화", "수", "목", "금", "토", "일"};
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setFont(NORMAL_FONT);
        ((JComponent) dayComboBox.getRenderer()).setOpaque(true);
        
        // 결과 출력 영역 (JTextArea)
        resultArea = new JTextArea(10, 20); // 10행, 20열 크기 설정
        resultArea.setFont(NORMAL_FONT);
        resultArea.setEditable(false); // 사용자가 수정할 수 없도록 설정
        resultArea.setLineWrap(true); // 텍스트가 너무 길어지면 자동 줄바꿈
        resultArea.setWrapStyleWord(true); // 단어 단위로 줄바꿈
        resultArea.setBackground(SECONDARY_COLOR);
        resultArea.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR)); // 테두리 설정
       }
    


    private void layoutComponents() {
        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 타이틀 패널
        JPanel titlePanel = createStyledPanel();
        JLabel titleLabel = new JLabel("학식 메뉴 추천 시스템", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // 예산 입력 패널
        JPanel budgetPanel = createStyledPanel();
        budgetPanel.setBorder(createStyledTitledBorder("예산 설정"));
        JLabel budgetLabel = new JLabel("예산 입력: ");
        budgetLabel.setFont(NORMAL_FONT);
        budgetPanel.add(budgetLabel);
        budgetPanel.add(budgetField);
        budgetPanel.add(new JLabel("원"));
        mainPanel.add(budgetPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // 선호도 입력 패널
        JPanel preferencesPanel = createStyledPanel();
        preferencesPanel.setLayout(new BoxLayout(preferencesPanel, BoxLayout.Y_AXIS));
        preferencesPanel.setBorder(createStyledTitledBorder("메뉴 선호도 설정"));

        for (Map.Entry<String, List<Menu>> entry : buildings.entrySet()) {
            // 건물별로 패널을 만들고 서브메뉴를 처리
            if (entry.getKey().equals("기숙사")) {
                JPanel dormPanel = createStyledPanel();
                dormPanel.setLayout(new BoxLayout(dormPanel, BoxLayout.Y_AXIS));
                dormPanel.setBorder(createStyledTitledBorder(entry.getKey()));

                // 한식 메뉴
                JPanel koreanFoodPanel = createStyledPanel();
                koreanFoodPanel.setLayout(new BoxLayout(koreanFoodPanel, BoxLayout.Y_AXIS));
                koreanFoodPanel.setBorder(createStyledTitledBorder("한식"));

                // 한식 서브메뉴에 대한 선호도 입력
                for (Menu menu : entry.getValue()) {
                    if (menu.name.equals("한식")) {
                        for (Menu subMenu : koreanFoodSubMenus) {
                            JPanel menuPanel = createStyledPanel();
                            menuPanel.setLayout(new BorderLayout(10, 0));

                            JLabel menuLabel = new JLabel(subMenu.name);
                            menuLabel.setFont(NORMAL_FONT);
                            menuLabel.setPreferredSize(new Dimension(100, 25));

                            JSlider slider = createStyledSlider();
                            preferenceSliders.put(subMenu, slider);

                            menuPanel.add(menuLabel, BorderLayout.WEST);
                            menuPanel.add(slider, BorderLayout.CENTER);
                            koreanFoodPanel.add(menuPanel);
                            koreanFoodPanel.add(Box.createVerticalStrut(5));
                        }
                        dormPanel.add(koreanFoodPanel);
                        dormPanel.add(Box.createVerticalStrut(10));
                    }
                }
                // 라면 메뉴
                JPanel ramenPanel = createStyledPanel();
                ramenPanel.setLayout(new BoxLayout(ramenPanel, BoxLayout.Y_AXIS));
                ramenPanel.setBorder(createStyledTitledBorder("라면"));

                // 라면 서브메뉴에 대한 선호도 입력
                for (Menu menu : entry.getValue()) {
                    if (menu.name.equals("라면")) {
                        for (Menu subMenu : ramenSubMenus) {
                            JPanel menuPanel = createStyledPanel();
                            menuPanel.setLayout(new BorderLayout(10, 0));

                            JLabel menuLabel = new JLabel(subMenu.name);
                            menuLabel.setFont(NORMAL_FONT);
                            menuLabel.setPreferredSize(new Dimension(100, 25));

                            JSlider slider = createStyledSlider();
                            preferenceSliders.put(subMenu, slider);

                            menuPanel.add(menuLabel, BorderLayout.WEST);
                            menuPanel.add(slider, BorderLayout.CENTER);
                            ramenPanel.add(menuPanel);
                            ramenPanel.add(Box.createVerticalStrut(5));
                        }
                        dormPanel.add(ramenPanel);
                        dormPanel.add(Box.createVerticalStrut(10));
                    }
                }

                preferencesPanel.add(dormPanel);
            } else {
                // 다른 건물에 대한 패널 처리
                JPanel buildingPanel = createStyledPanel();
                buildingPanel.setLayout(new BoxLayout(buildingPanel, BoxLayout.Y_AXIS));
                buildingPanel.setBorder(createStyledTitledBorder(entry.getKey()));

                for (Menu menu : entry.getValue()) {
                    JPanel menuPanel = createStyledPanel();
                    menuPanel.setLayout(new BorderLayout(10, 0));

                    JLabel menuLabel = new JLabel(menu.name);
                    menuLabel.setFont(NORMAL_FONT);
                    menuLabel.setPreferredSize(new Dimension(100, 25));

                    JSlider slider = createStyledSlider();
                    preferenceSliders.put(menu, slider);

                    menuPanel.add(menuLabel, BorderLayout.WEST);
                    menuPanel.add(slider, BorderLayout.CENTER);
                    buildingPanel.add(menuPanel);
                    buildingPanel.add(Box.createVerticalStrut(5));
                }
                preferencesPanel.add(buildingPanel);
            }

            preferencesPanel.add(Box.createVerticalStrut(10));
        }
        mainPanel.add(preferencesPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // 요일 선택 패널
        JPanel dayPanel = createStyledPanel();
        dayPanel.setBorder(createStyledTitledBorder("요일 선택"));
        JLabel dayLabel = new JLabel("요일: ");
        dayLabel.setFont(NORMAL_FONT);
        dayPanel.add(dayLabel);
        dayPanel.add(dayComboBox);
        mainPanel.add(dayPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // 추천 버튼
        JButton recommendButton = createStyledButton("메뉴 추천받기");
        recommendButton.addActionListener(e -> recommendMenus());
        mainPanel.add(recommendButton);
        mainPanel.add(Box.createVerticalStrut(15));

        // 결과 영역
        resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(createStyledTitledBorder("추천 결과"));
        mainPanel.add(scrollPane);

        // 전체 패널을 스크롤 패널에 추가
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBorder(null);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScrollPane);
    }
 
    private JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_COLOR);
        return panel;
    }

    private JSlider createStyledSlider() {
        JSlider slider = new JSlider(1, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(SECONDARY_COLOR);
        slider.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
        return slider;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(NORMAL_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 마우스 오버 효과
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    
    private Map<String, List<Menu>> initializeMenus() {
        Map<String, List<Menu>> buildings = new HashMap<>();

        // 한식 서브메뉴들
        koreanFoodSubMenus = new ArrayList<>();
        koreanFoodSubMenus.add(new Menu("불고기", 0, "기숙사"));
        koreanFoodSubMenus.add(new Menu("김치찌개", 0, "기숙사"));
        koreanFoodSubMenus.add(new Menu("잡채", 0, "기숙사"));
        koreanFoodSubMenus.add(new Menu("비빔밥", 0, "기숙사"));
        koreanFoodSubMenus.add(new Menu("쏘야", 0, "기숙사"));

        // 라면 서브메뉴들
        ramenSubMenus = new ArrayList<>();
        ramenSubMenus.add(new Menu("치즈라면", 0, "기숙사"));

        // 학생회관 메뉴들
        List<Menu> studentUnion = new ArrayList<>();
        studentUnion.add(new Menu("마제소바", 7500, "학생회관"));
        studentUnion.add(new Menu("우육면", 8000, "학생회관"));
        studentUnion.add(new Menu("마파두부덮밥", 6500, "학생회관"));
        studentUnion.add(new Menu("순두부찌개", 6000, "학생회관"));
        studentUnion.add(new Menu("된장찌개", 5000, "학생회관"));
        buildings.put("학생회관", studentUnion);

        // 기숙사 메뉴들
        List<Menu> dormitory = new ArrayList<>();
        dormitory.add(new Menu("라면", 4000, "기숙사"));
        dormitory.add(new Menu("한식", 6000, "기숙사"));
        dormitory.add(new Menu("양식", 7000, "기숙사"));
        buildings.put("기숙사", dormitory);

        return buildings;
    }
    
    private TitledBorder createStyledTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                NORMAL_FONT,
                PRIMARY_COLOR
        );
        border.setTitleFont(NORMAL_FONT);
        return border;
    }


    private void recommendMenus() {
        try {
            // 예산 가져오기
            int budget = Integer.parseInt(budgetField.getText());
            if (budget <= 0) {
                JOptionPane.showMessageDialog(this, "유효한 예산을 입력해주세요.");
                return;
            }

            // 각 메뉴에 선호도 설정
            for (Map.Entry<Menu, JSlider> entry : preferenceSliders.entrySet()) {
                entry.getKey().setPreferenceScore(entry.getValue().getValue());
            }

            // 선택된 요일에 따른 메뉴 필터링
            String selectedDay = (String) dayComboBox.getSelectedItem();
            List<Menu> availableMenus = selectMenusForDay(selectedDay, buildings);

            if (availableMenus.isEmpty()) {
                resultArea.setText("해당 요일에는 운영하지 않습니다.");
                return;
            }

            availableMenus.sort((m1, m2) -> {
                double m1Efficiency = (double) m1.preferenceScore / m1.price;
                double m2Efficiency = (double) m2.preferenceScore / m2.price;
                return Double.compare(m2Efficiency, m1Efficiency);
            });


            // Greedy Algorithm: 예산 내에서 메뉴 선택
            List<Menu> selectedMenus = new ArrayList<>();
            int remainingBudget = budget;
            List<String> buildingNames = new ArrayList<>();
            Map<Menu, String> menuToBuildingMap = new HashMap<>();

            // 각 메뉴가 어느 건물에 속하는지 미리 저장
            for (Map.Entry<String, List<Menu>> entry : buildings.entrySet()) {
                for (Menu menu : entry.getValue()) {
                    menuToBuildingMap.put(menu, entry.getKey());
                }
            }

         // 메뉴 선택: 예산 내에서 가장 선호도 높은 메뉴부터 선택
            for (Menu menu : availableMenus) {
                // 기숙사 한식일 경우, 서브메뉴 선호도 합계를 기반으로 계산
                if (menu.name.equals("한식") && menuToBuildingMap.get(menu).equals("기숙사")) {
                    // 한식 서브메뉴들의 선호도 합계를 기반으로 1~10로 변환
                    int totalKoreanFoodPreference = koreanFoodSubMenus.stream()
                            .mapToInt(subMenu -> subMenu.preferenceScore) // preferenceScore 필드 접근
                            .sum();
                    int normalizedKoreanPreference = (int) Math.round((double) totalKoreanFoodPreference / (koreanFoodSubMenus.size() * 10) * 10);

                    // 계산된 한식의 선호도로 menu 객체의 선호도를 업데이트
                    menu.preferenceScore = normalizedKoreanPreference;
                }

                if (menu.price <= remainingBudget) {
                    selectedMenus.add(menu);
                    remainingBudget -= menu.price;

                    // 해당 메뉴가 포함된 건물 이름 추가
                    String buildingName = menuToBuildingMap.get(menu);
                    buildingNames.add(buildingName);
                }
            }
            
            // 결과 출력
            if (selectedMenus.isEmpty()) {
                resultArea.setText("예산 내에서 선택할 수 있는 메뉴가 없습니다.");
            } else {
                StringBuilder result = new StringBuilder("추천 메뉴:\n\n");
                for (Menu menu : selectedMenus) {
                    result.append(menu.toString()).append("\n");
                }
                result.append("\n남은 예산: ").append(remainingBudget).append("원");
                resultArea.setText(result.toString());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "올바른 예산을 입력해주세요.");
        }
    }
    
    private List<Menu> selectMenusForDay(String day, Map<String, List<Menu>> buildings) {
        List<Menu> availableMenus = new ArrayList<>();

        // 주말(토,일) 체크
        if (day.equals("토") || day.equals("일")) {
            return availableMenus; // 빈 리스트 반환
        }

        // 평일 요일별로 조건에 맞는 메뉴 필터링
        switch (day) {
            case "월":
            case "수":
            case "금":
                // 월, 수, 금은 기숙사 메뉴 추천
                if (buildings.containsKey("기숙사")) {
                    availableMenus.addAll(buildings.get("기숙사"));
                }
                break;
            case "화":
            case "목":
                if (buildings.containsKey("학생회관")) {
                    availableMenus.addAll(buildings.get("학생회관"));
                }
                break;
            default:
                // 잘못된 요일 입력 시 빈 리스트 반환
                break;
        }

        return availableMenus;
    }

    // main 메서드 추가
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OptiMeal().setVisible(true);
        });
    }
}