package application;

import javafx.scene.control.ButtonType;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.world.*;
import model.collectibles.*;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;	
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.awt.Rectangle;
import java.io.File;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import model.characters.*;

public class Main extends Application {
	public static GridPane root3 = new GridPane();
	static Stage stage = new Stage();
	StackPane root = new StackPane();
	Scene scene = new Scene(root,Color.BLACK);
	StackPane root2 = new StackPane();
	Scene scene2 = new Scene(root2,Color.BLACK);
	BorderPane game = new BorderPane();
	Scene scene3 = new Scene(game,Color.BLACK);
	
	public static void main(String[] args) {
		launch(args);
		GridPane root3 = new GridPane();
		root3.setAlignment(Pos.CENTER);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root,Color.BLACK);
		StackPane root2 = new StackPane();
		Scene scene2 = new Scene(root2,Color.BLACK);
		root3.setAlignment(Pos.CENTER);
		BorderPane game = new BorderPane();
		Scene scene3 = new Scene(game,Color.BLACK);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		scene2.getStylesheets().add(css);
		scene3.getStylesheets().add(css);
		Game.loadHeroes("C:/Users/20122/Downloads/Milestone2-Solution/Heroes.csv");
		game.setCenter(root3);


		String path= "/Milestone2-Solution/src/Theme song.mp3"; 
		Button button = new Button("Play Sound");
		button.setText("SOUND");
		Media sound = new Media(new File(path).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		button.setOnAction(event -> mediaPlayer.play());
		root.getChildren().add(button);

		//background img
		Image Icon = new Image("Logo.jpg");
		Image bg = new Image("bg.png");
		ImageView bgv= new ImageView(bg);
		root.getChildren().add(bgv);
		Image bg2 = new Image("bgg.jpg");
		ImageView bgv2= new ImageView(bg2);
		root2.getChildren().add(bgv2);
		Image bg3 = new Image("bggb.jpg");
		BackgroundImage gridBk = new BackgroundImage(bg3, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO , false, false, false, true));
		root3.setBackground(new Background(gridBk));
		game.setBackground(new Background(gridBk));
		for(int i = 0; i<15; i++) {
			for(int j = 0; j<15; j++) {
				ImageView tpview = new ImageView();
				tpview.setFitHeight(70);
				tpview.setFitWidth(100);
				root3.add(tpview, j, 14-i);
				map[i][j]=tpview;
				tpview.setFitHeight(70);
				tpview.setFitWidth(70);
				tpview.setOnMouseEntered(e ->{
					columnIndex = root3.getColumnIndex((ImageView) e.getSource());
					rowIndex = root3.getRowIndex((ImageView) e.getSource());
					Hero heroHover = null;
					if ( Game.map[14-rowIndex][columnIndex] instanceof CharacterCell && ( (CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter() instanceof Hero) {
						heroHover = (Hero) ((CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter();
						String heroHoverType;
						if(heroHover instanceof Medic)
							heroHoverType = "Medic"; 
						else if(heroHover instanceof Explorer)
							heroHoverType = "Explorer"; 
						else 
							heroHoverType = "Fighter";
						String tooltipText = "Name: "+ heroHover.getName() + "\n" +
								"Current HP: "+ heroHover.getCurrentHp() + "\n" + 
								"Attack Damage: " + 	heroHover.getAttackDmg() + "\n" +
								"Max Actions: " + heroHover.getMaxActions() + "\n" + 
								"Actions Available: "+ heroHover.getActionsAvailable() + "\n" +
								"Available Vaccines: " + heroHover.getVaccineInventory().size() + "\n" + 
								"Available Supplies: "+ heroHover.getSupplyInventory().size() + "\n" +
								"Type : " + heroHoverType;
						Tooltip tooltip = new Tooltip(tooltipText);
						tooltip.setShowDelay(javafx.util.Duration.millis(100)); // Set the delay to zero
						tooltip.setStyle("-fx-font-size: 18px; -fx-background-color: Red; -fx-text-fill: black");		
						Tooltip.install(tpview, tooltip);
					}
					else if ( Game.map[14-rowIndex][columnIndex] instanceof CharacterCell && ( (CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter() instanceof Zombie) {
						Zombie zombieHover = null; 
						zombieHover = (Zombie) ((CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter();
						String tooltipText = "HP: "+ zombieHover.getCurrentHp() + "\n" + 
								"Damage: " + 	zombieHover.getAttackDmg();
						Tooltip tooltip = new Tooltip(tooltipText);

						tooltip.setStyle("-fx-font-size: 18px; -fx-background-color: Red; -fx-text-fill: black");		
						Tooltip.install(tpview, tooltip);
					}
				});
				tpview.setOnMouseClicked(e->{
					int col = root3.getColumnIndex((ImageView)e.getSource());
					int row= root3.getRowIndex((ImageView)e.getSource());
					if (Game.map[14-row][col] instanceof CharacterCell) {
						if(((CharacterCell) (Game.map[14-row][col])).getCharacter() instanceof Hero) {
							currentHero =(Hero) ((CharacterCell) (Game.map[14-row][col])).getCharacter();


						}
						if(((CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter() instanceof Hero && medicUlt) {
							currentHero.setTarget((Hero) ((CharacterCell) Game.map[14-rowIndex][columnIndex]).getCharacter());
							medicUlt = false;
						}
						else if(((CharacterCell) (Game.map[14-row][col])).getCharacter() instanceof Zombie) {
							currentHero.setTarget(((CharacterCell) (Game.map[14-row][col])).getCharacter());

						}

					}
				});





			}
		}


		//start button
		Button start=new Button();
		start.setText("Start Game");
		start.setFont(Font.font("Impact",50));
		start.setTranslateX(750);
		start.setTranslateY(50);
		start.getStyleClass().add("sceneno1");
		root.getChildren().add(start);
		start.setOnMouseEntered(event -> {
			start.setStyle("-fx-text-fill: brown");
		});	
		start.setOnMouseExited(event -> {
			start.setStyle("-fx-text-fill: black");
		});
		start.setOnAction(e -> {
			stage.setScene(scene2);
			stage.setFullScreen(true);

		});



		//zorar exit
		Button Exit=new Button();
		Exit.setText("Exit");
		Exit.setTranslateX(800);
		Exit.setTranslateY(150);
		Exit.setFont(Font.font("Impact",50));
		Exit.getStyleClass().add("sceneno1");
		root.getChildren().add(Exit);
		Exit.setOnMouseEntered(event -> {
			Exit.setStyle("-fx-text-fill: brown");
		});	
		Exit.setOnMouseExited(event -> {
			Exit.setStyle("-fx-text-fill: black");
		});
		Exit.setOnAction(e -> Platform.exit());

		Text Fighter = new Text();
		Fighter.setText("Fighters");
		Fighter.setTranslateX(-800);
		Fighter.setTranslateY(-275);
		Fighter.setFill(Color.ANTIQUEWHITE);
		Fighter.setFont(Font.font("Impact",50));
		Text Medic = new Text();
		Medic.setText("Medics");
		Medic.setTranslateX(-820);
		Medic.setTranslateY(-60);
		Medic.setFill(Color.ANTIQUEWHITE);
		Medic.setFont(Font.font("Impact",50));
		Text Explorer = new Text();
		Explorer.setText("Explorers");
		Explorer.setTranslateX(-800);
		Explorer.setTranslateY(200);
		Explorer.setFill(Color.ANTIQUEWHITE);
		Explorer.setFont(Font.font("Impact",50));
		root2.getChildren().add(Fighter);
		root2.getChildren().add(Medic);
		root2.getChildren().add(Explorer);
		Text Hero= new Text();
		Hero.setText("Select Your Hero");
		Hero.setTranslateX(0);
		Hero.setTranslateY(-450);
		Hero.setFill(Color.ANTIQUEWHITE);
		Hero.setFont(Font.font("Impact",70));
		root2.getChildren().add(Hero);

		//Character Buttons
		Button Joel = new Button();
		Joel.getStyleClass().add("sceneno2");

		Text jstats= new Text();
		jstats.setText("Health : 140    Attack Damage : 35   Action Points : 5");
		jstats.setFill(Color.CYAN);
		jstats.setFont(Font.font("Verdana",30));
		jstats.setTranslateX(400);
		jstats.setTranslateY(450);
		jstats.setVisible(false);
		root2.getChildren().add(jstats);
		Image Joe= new Image ("Joel Miller.png");
		ImageView joeView = new ImageView(Joe);
		joeView.setFitHeight(500);
		joeView.setFitWidth(550);
		joeView.setTranslateX(400);
		joeView.setTranslateY(-50);
		joeView.setVisible(false);
		root2.getChildren().add(joeView);
		Joel.setOnMouseEntered(event -> {
			joeView.setVisible(true);
			jstats.setVisible(true);
			Joel.setStyle("-fx-text-fill: brown");
		});	
		Joel.setOnMouseExited(event -> {
			joeView.setVisible(false);
			jstats.setVisible(false);
			Joel.setStyle("-fx-text-fill: BurlyWood");
		});
		Joel.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Joel Miller")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Joel.setText("Joel Miller");
		Joel.setTranslateX(-810);
		Joel.setTranslateY(-220);
		Joel.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Joel);


		Button David = new Button();
		David.getStyleClass().add("sceneno2");
		David.setText("David");
		David.setTranslateX(-810);
		David.setTranslateY(-170);
		David.setFont(Font.font("Verdana",28));
		root2.getChildren().add(David);

		Text dstats= new Text();
		dstats.setText("Health : 150    Attack Damage : 35   Action Points : 4");
		dstats.setFill(Color.CYAN);
		dstats.setFont(Font.font("Verdana",30));
		dstats.setTranslateX(400);
		dstats.setTranslateY(450);
		dstats.setVisible(false);
		root2.getChildren().add(dstats);
		Image dav= new Image ("David.png");
		ImageView DavidView = new ImageView(dav);
		DavidView.setFitHeight(500);
		DavidView.setFitWidth(550);
		DavidView.setTranslateX(400);
		DavidView.setTranslateY(-50);
		DavidView.setVisible(false);
		root2.getChildren().add(DavidView);
		David.setOnMouseEntered(event -> {
			DavidView.setVisible(true);
			dstats.setVisible(true);
			David.setStyle("-fx-text-fill: brown");
		});	
		David.setOnMouseExited(event -> {
			DavidView.setVisible(false);
			dstats.setVisible(false);
			David.setStyle("-fx-text-fill: BurlyWood");
		});
		David.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("David")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Bill = new Button();
		Bill.getStyleClass().add("sceneno2");

		Bill.setText("Bill");
		Bill.setTranslateX(-820);
		Bill.setTranslateY(-10);
		Bill.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Bill);

		Text bstats= new Text();
		bstats.setText("Health : 100    Attack Damage : 10   Action Points : 7");
		bstats.setFill(Color.CYAN);
		bstats.setFont(Font.font("Verdana",30));
		bstats.setTranslateX(400);
		bstats.setTranslateY(450);
		bstats.setVisible(false);
		root2.getChildren().add(bstats);
		Image bill= new Image ("Bill.png");
		ImageView Billview = new ImageView(bill);
		Billview.setFitHeight(500);
		Billview.setFitWidth(550);
		Billview.setTranslateX(400);
		Billview.setTranslateY(-50);
		Billview.setVisible(false);
		root2.getChildren().add(Billview);
		Bill.setOnMouseEntered(event -> {
			Bill.setStyle("-fx-text-fill: brown");
			Billview.setVisible(true);
			bstats.setVisible(true);
		});	
		Bill.setOnMouseExited(event -> {
			Billview.setVisible(false);
			bstats.setVisible(false);
			Bill.setStyle("-fx-text-fill: BurlyWood");
		});
		Bill.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Bill")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Henry = new Button();
		Henry.getStyleClass().add("sceneno2");
		Henry.setText("Henry Burell");
		Henry.setTranslateX(-820);
		Henry.setTranslateY(40);
		Henry.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Henry);

		Text hstats= new Text();
		hstats.setText("Health : 105    Attack Damage : 15   Action Points : 6");
		hstats.setFill(Color.CYAN);
		hstats.setFont(Font.font("Verdana",30));
		hstats.setTranslateX(400);
		hstats.setTranslateY(450);
		hstats.setVisible(false);
		root2.getChildren().add(hstats);
		Image henry= new Image ("Henry Burell.png");
		ImageView henryview = new ImageView(henry);
		henryview.setFitHeight(500);
		henryview.setFitWidth(550);
		henryview.setTranslateX(400);
		henryview.setTranslateY(-50);
		henryview.setVisible(false);
		root2.getChildren().add(henryview);
		Henry.setOnMouseEntered(event -> {
			henryview.setVisible(true);
			hstats.setVisible(true);
			Henry.setStyle("-fx-text-fill: brown");
		});	
		Henry.setOnMouseExited(event -> {
			henryview.setVisible(false);
			hstats.setVisible(false);
			Henry.setStyle("-fx-text-fill: BurlyWood");
		});
		Henry.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Henry Burell")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Ellie = new Button();
		Ellie.getStyleClass().add("sceneno2");
		Ellie.setText("Ellie Williams");
		Ellie.setTranslateX(-820);
		Ellie.setTranslateY(90);
		Ellie.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Ellie);

		Text estats= new Text();
		estats.setText("Health : 110    Attack Damage : 15   Action Points : 6");
		estats.setFill(Color.CYAN);
		estats.setFont(Font.font("Verdana",30));
		estats.setTranslateX(400);
		estats.setTranslateY(450);
		estats.setVisible(false);
		root2.getChildren().add(estats);
		Image ellie= new Image ("Ellie Williams.png");
		ImageView ellieview = new ImageView(ellie);
		ellieview.setFitHeight(500);
		ellieview.setFitWidth(550);
		ellieview.setTranslateX(400);
		ellieview.setTranslateY(-50);
		ellieview.setVisible(false);
		root2.getChildren().add(ellieview);
		Ellie.setOnMouseEntered(event -> {
			ellieview.setVisible(true);
			estats.setVisible(true);
			Ellie.setStyle("-fx-text-fill: brown");
		});	
		Ellie.setOnMouseExited(event -> {
			ellieview.setVisible(false);
			estats.setVisible(false);
			Ellie.setStyle("-fx-text-fill: BurlyWood");
		});
		Ellie.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Ellie Williams")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Tess = new Button();
		Tess.getStyleClass().add("sceneno2");
		Tess.setText("Tess");
		Tess.setTranslateX(-820);
		Tess.setTranslateY(260);
		Tess.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Tess);

		Text tstats= new Text();
		tstats.setText("Health : 80    Attack Damage : 20   Action Points : 6");
		tstats.setFill(Color.CYAN);
		tstats.setFont(Font.font("Verdana",30));
		tstats.setTranslateX(400);
		tstats.setTranslateY(450);
		tstats.setVisible(false);
		root2.getChildren().add(tstats);
		Image tess= new Image ("Tess.png");
		ImageView tessview = new ImageView(tess);
		tessview.setFitHeight(500);
		tessview.setFitWidth(550);
		tessview.setTranslateX(400);
		tessview.setTranslateY(-50);
		tessview.setVisible(false);
		root2.getChildren().add(tessview);
		Tess.setOnMouseEntered(event -> {
			tessview.setVisible(true);
			tstats.setVisible(true);
			Tess.setStyle("-fx-text-fill: brown");
		});	
		Tess.setOnMouseExited(event -> {
			tessview.setVisible(false);
			tstats.setVisible(false);
			Tess.setStyle("-fx-text-fill: BurlyWood");
		});
		Tess.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Tess")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Riley = new Button();
		Riley.getStyleClass().add("sceneno2");
		Riley.setText("Riley Abel");
		Riley.setTranslateX(-820);
		Riley.setTranslateY(310);
		Riley.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Riley);

		Text rstats= new Text();
		rstats.setText("Health : 90    Attack Damage : 25   Action Points : 5");
		rstats.setFill(Color.CYAN);
		rstats.setFont(Font.font("Verdana",30));
		rstats.setTranslateX(400);
		rstats.setTranslateY(450);
		rstats.setVisible(false);
		root2.getChildren().add(rstats);
		Image riley= new Image ("Riley Abel.png");
		ImageView rileyview = new ImageView(riley);
		rileyview.setFitHeight(500);
		rileyview.setFitWidth(550);
		rileyview.setTranslateX(400);
		rileyview.setTranslateY(-50);
		rileyview.setVisible(false);
		root2.getChildren().add(rileyview);
		Riley.setOnMouseEntered(event -> {
			rileyview.setVisible(true);
			rstats.setVisible(true);
			Riley.setStyle("-fx-text-fill: brown");
		});	
		Riley.setOnMouseExited(event -> {
			rileyview.setVisible(false);
			rstats.setVisible(false);
			Riley.setStyle("-fx-text-fill: BurlyWood");
		});
		Riley.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Riley Abel")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});
		Button Tommy = new Button();
		Tommy.getStyleClass().add("sceneno2");
		Tommy.setText("Tommy Miller");
		Tommy.setTranslateX(-820);
		Tommy.setTranslateY(360);
		Tommy.setFont(Font.font("Verdana",28));
		root2.getChildren().add(Tommy);

		Text tostats= new Text();
		tostats.setText("Health : 95    Attack Damage : 25   Action Points : 5");
		tostats.setFill(Color.CYAN);
		tostats.setFont(Font.font("Verdana",30));
		tostats.setTranslateX(400);
		tostats.setTranslateY(450);
		tostats.setVisible(false);
		root2.getChildren().add(tostats);
		Image tommy= new Image ("Tommy Miller.png");
		ImageView tommyview = new ImageView(tommy);
		tommyview.setFitHeight(500);
		tommyview.setFitWidth(550);
		tommyview.setTranslateX(400);
		tommyview.setTranslateY(-50);
		tommyview.setVisible(false);
		root2.getChildren().add(tommyview);
		Tommy.setOnMouseEntered(event -> {
			tommyview.setVisible(true);
			tostats.setVisible(true);
			Tommy.setStyle("-fx-text-fill: brown");
		});	
		Tommy.setOnMouseExited(event -> {
			tommyview.setVisible(false);
			tostats.setVisible(false);
			Tommy.setStyle("-fx-text-fill: BurlyWood");
		});
		Tommy.setOnAction(e -> {
			stage.setScene(scene3);
			stage.setFullScreen(true);
			for(int i = 0;i<Game.availableHeroes.size();i++) {
				if(Game.availableHeroes.get(i).getName().equals("Tommy Miller")) {
					startgame(Game.availableHeroes.get(i));
				}
			}
		});

		//icon
		stage.getIcons().add(Icon);
		stage.setTitle("The Last Of Us");
		stage.setWidth(900);
		stage.setHeight(900);
		stage.setFullScreen(true);

		scene3.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode()==KeyCode.W) 
				move( currentHero , Direction.UP);
		});
		scene3.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode()==KeyCode.S) 
				move( currentHero , Direction.DOWN);
		});
		scene3.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode()==KeyCode.D) 
				move( currentHero , Direction.RIGHT);
		});
		scene3.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if(key.getCode()==KeyCode.A) 
				move( currentHero , Direction.LEFT);
		});

		Button Attack=new Button();
		Image attack= new Image("Attack button.png");
		ImageView Av= new ImageView(attack);
		Attack.getStyleClass().add("sceneno3");
		Av.setFitHeight(80);
		Av.setFitWidth(150);
		Attack.setGraphic(Av);
		Attack.setTranslateY(600);
		Attack.setTranslateX(-308);
		game.setRight(Attack);



		Attack.setOnAction(e -> {
			try {	
				currentHero.attack();

			}
			catch(NotEnoughActionsException n) {

			}
			catch(InvalidTargetException n) {
				Alert a = new Alert(AlertType.WARNING, "Select a valid Target");
				a.show();
				stage.setFullScreen(true);

			}

		});

		Button Cure=new Button();
		Image cure= new Image("cure.png");
		ImageView curev= new ImageView(cure);
		Cure.getStyleClass().add("sceneno3");
		curev.setFitHeight(80);
		curev.setFitWidth(150);
		Cure.setGraphic(curev);
		Cure.setTranslateY(700);
		Cure.setTranslateX(-140);
		Cure.setOnAction(e -> {
			try {	
				currentHero.cure();
				currentHero.setTarget(null);

			}
			catch(NoAvailableResourcesException a) {
				Alert c = new Alert(AlertType.WARNING, "You have no Vaccines");
				c.show();
				stage.setFullScreen(true);


			}catch(InvalidTargetException a) {
				Alert b = new Alert(AlertType.WARNING, "Select a valid Target");
				b.show();
				stage.setFullScreen(true);


			}catch(NotEnoughActionsException a) {


			}

		});
		root3.setGridLinesVisible(true);

		//Special button
		Button Special=new Button();
		Image Specialv= new Image("supply2.png");
		ImageView sv= new ImageView(Specialv);
		Special.getStyleClass().add("sceneno3");
		sv.setFitHeight(80);
		sv.setFitWidth(150);
		Special.setGraphic(sv);
		Special.setTranslateY(800);
		Special.setTranslateX(26);
		Special.setOnAction(e -> {
			try{

				if(currentHero instanceof Medic && (currentHero.getTarget() == null || currentHero.getTarget() instanceof Zombie)) {
					medicUlt = true;
					return;
				}
				else if(!(currentHero instanceof Medic))
					currentHero.setTarget(currentHero.getTarget());
				currentHero.useSpecial();
			}catch(NoAvailableResourcesException f) {
				Alert b = new Alert(AlertType.WARNING, "No Available Supplies");
				b.show();
				stage.setFullScreen(true);
			}catch(InvalidTargetException f) {
				Alert b = new Alert(AlertType.WARNING, "Invalid Target");
				b.show();
				stage.setFullScreen(true);
			}
			Visibility();
		});
		Button Endturn=new Button();
		Image End= new Image("Endturn.png");
		ImageView Endturnv= new ImageView(End);
		Endturn.getStyleClass().add("sceneno3");
		Endturnv.setFitHeight(80);
		Endturnv.setFitWidth(150);
		Endturn.setGraphic(Endturnv);
		Endturn.setTranslateY(900);
		Endturn.setTranslateX(-472);
		Endturn.setOnAction(e -> {
			try{
				Game.endTurn();

			}catch(NotEnoughActionsException f) {

			}catch(InvalidTargetException f) {

			}
			Visibility();
		});
		HBox buttonContainer = new HBox(0); 
		buttonContainer.getChildren().addAll(Special, Cure, Attack,Endturn);
		game.setRight(buttonContainer);
		ImageView lftv= new ImageView();	
		Text Cont= new Text();
		Cont.setText("Controls are"+"\n"+"\n"+"  W , S , D, A");
		Cont.setFont(Font.font("Impact",50));
		Cont.setFill(Color.ANTIQUEWHITE);
		Cont.setTranslateY(150);;
		lftv.setFitWidth(300);
		game.setLeft(Cont);

		stage.setScene(scene);
		stage.show();
		stage.setFullScreen(true);



	}

	public static void EndTurn(Hero h) throws NotEnoughActionsException, InvalidTargetException {
		Game.endTurn();

	}
	public static void Visibility() {
		for (int i = 0 ; i<15 ; i++)
			for (int j = 0 ; j<15 ; j++) {	
				if (!(Game.map[i][j].isVisible()) ) {
					map[i][j].setVisible(false);
				}
				else
					map[i][j].setVisible(true);

			}
	}		
	public static void checkNewZombies()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{


				if (Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Zombie
						&& map[i][j].getImage() == null)
					map[i][j].setImage(new Image("zombie.png"));
			}
	}

	public static void checkDeadHeroes()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
			{
				if (Game.map[i][j] instanceof CharacterCell 
						&& ((CharacterCell)Game.map[i][j]).getCharacter() == null
						&& map[i][j].getImage() != null)
					map[i][j].setImage(null);
			}

		if (currentHero != null && currentHero.getCurrentHp() == 0)
			currentHero = null;
	}
	public static void startgame(Hero h) {	
		currentHero = h;
		Game.startGame(h);		
		spawnCharacter(h);

		for(int y= 0 ; y < Game.zombies.size();y++){
			spawnCharacter(Game.zombies.get(y));
		}
		Visibility();


	}
	public static void click() {

	}
	public static void spawnCharacter(Character c) { 
		int x=c.getLocation().x;
		int y=c.getLocation().y;
		ImageView test = map[x][y];

		if(c instanceof Zombie) {
			Image ch = new Image("zombie.png");            
			test.setImage(ch);
			Visibility();

		}else {
			Image ch = new Image(c.getName()+"1"+".png"); 
			test.setImage(ch);
			Visibility();

		}
	}
	public static void spawncollectible(int x, int y, Collectible c){
		if(c instanceof Vaccine ) {
			Image v= new Image("vaccine.png");
			map[x][y].setImage(v);
		}
		else {
			Image v= new Image("supply2.png");
			map[x][y].setImage(v);


		}
	}
	public  static void trapwarn() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("You Have stepped in a trap");
		alert.setHeaderText(null);
		alert.setContentText("This is a pop-up message!");

		alert.showAndWait();
	}
	public  static void Gamewon() {
		ButtonType okButton = ButtonType.OK;
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Congrats");
		alert.setHeaderText(null);
		alert.setContentText("Win");


		alert.showAndWait();
		alert.showAndWait().ifPresent(buttonType -> {
			if (buttonType == okButton) {
				Platform.exit();
			}
		});
	}
	public  static void Gameover() {
		ButtonType okButton = ButtonType.OK;
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Try AGain");
		alert.setHeaderText(null);
		alert.setContentText("Game Over");


		alert.showAndWait();
		alert.showAndWait().ifPresent(buttonType -> {
			if (buttonType == okButton) {
				Platform.exit();
			}
		});
	}


	public  static void NoActions() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No Actions");
		alert.setHeaderText(null);
		alert.setContentText("No more Actions available");
		alert.showAndWait();
	}
	public static void spawntraps(int x, int y){
		map[x][y].setImage(null);

	}



	public static boolean checkmove(int x , int y) {
		if(x>14||x<0||y>14||y<0)
			return false;
		else
			return true;
	}


	public static void move(Hero h , Direction d) {
//		currentHero.setActionsAvailable(1000);
		try {
			int x= h.getLocation().x;
			int y= h.getLocation().y;	
			h.move(d);
			ImageView hero = map[x][y];
			Image z = hero.getImage();
			hero.setImage(null);
			x = h.getLocation().x;
			y = h.getLocation().y;
			map[x][y].setImage(z);
			Game.adjustVisibility(h);


		}catch(MovementException e) {
			Alert b = new Alert(AlertType.WARNING, "InvalidMove");
			b.show();
			stage.setFullScreen(true);
		}catch(NotEnoughActionsException e) {

		}




	}


	public static Hero currentHero;	
	public static Zombie currentZombie;
	public static ImageView[][] map= new ImageView[15][15];
	public static Label stats;
	public static int columnIndex;
	public static int rowIndex;
	public static boolean medicUlt;
	public static Cell MapCell;


}