//package view;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//
///**
// * A játék main osztálya grafikus felülettel együtt
// *
// * @author Reményi Gergely
// * @since 2015-02-28
// */
//public class GameStage extends Application {
//
//
//    public static void main(String[] args) {
//
//        launch(args);
//
//        //Játékosok számának bekérése
//        /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int playerCount = 0;
//        while (playerCount == 0){
//            System.out.print("Jatekosok szama: ");
//            try{
//                playerCount = Integer.parseInt(br.readLine());
//            } catch (IOException e) {
//                System.out.println("Valami nagyon nem jo ha ez kiirodik");
//            } catch (NumberFormatException e) {
//                System.out.println("Rossz formatum");
//            }
//        }
//
//        //controller inicalizálása és elindítása
//        GameController controller = new GameController();
//        controller.setNumberOfPlayers(playerCount);
//        controller.initGame();*/
//
//    }
//
//    /**
//     * Elsődleges ablak beállítása
//     *
//     * Indulás után rögtön elindul
//     *
//     * @param primaryStage Az elsődleges ablak
//     *
//     * @throws Exception
//     */
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        primaryStage.setTitle("Phoebe");
//
//        MenuLayout menu = new MenuLayout(primaryStage);
//
//        primaryStage.setScene(menu.getParentScene());
//        primaryStage.show();
//    }
//}
