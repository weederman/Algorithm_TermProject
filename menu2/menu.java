package menu2;

class Menu {
    String name; // 메뉴의 이름
    int preferenceScore; // 메뉴의 선호도 (1~10)
    int price; // 메뉴의 가격 (원 단위)
    String buildingName; // 메뉴가 속한 건물 이름

    public Menu(String name, int price, String buildingName) {
        this.name = name;
        this.price = price;
        this.buildingName = buildingName;
        this.preferenceScore = 0;
    }

    public void setPreferenceScore(int score) {
        this.preferenceScore = score;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s : %d원", buildingName, name, price);

    }    
}