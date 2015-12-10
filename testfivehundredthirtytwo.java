import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage; 

public class testfivehundredthirtytwo extends Application {
	Stage window;
	ImageView myImageView = new ImageView();
	DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
	
	//Abus
	int rotation = 0;
	int totalCounter=0;
	int currentCounter = 0;
	int imageCounter =0;
	int first = 1;
	int next =0;
	File [] fileInput = new File [100];
	Image[] images = new Image [100];
	private int degreevalue = 0;
	final Label lbl = new Label("No images");
	final Label fxlbl = new Label("Effects");
	final Label navilbl = new Label("Navigate");
	
	final Label toplbl = new Label("Source");
	Button sepiaTone = new Button("Apply Sepia");
	
   public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Love in Pain");
        myImageView.setTranslateZ(myImageView.getBoundsInLocal().getWidth() / 2.0);
        toplbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        fxlbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        navilbl.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
  
        FileChooser fileChooser = new FileChooser();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(myImageView);

        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                myImageView.setFitWidth(zoomProperty.get() * 4);
                myImageView.setFitHeight(zoomProperty.get() * 3);
            }
        });
        
        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        
        
        //Open file button
        GridPane.setConstraints(toplbl, 0, 0);
        toplbl.setAlignment(Pos.CENTER);
        Button fileButton = new Button("Open File");
        GridPane.setConstraints(fileButton, 0, 1);    
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton,0, 2);
        Button reflect = new Button("Reflect");
        GridPane.setConstraints(fxlbl, 0, 4);
        fxlbl.setAlignment(Pos.CENTER);
        GridPane.setConstraints(reflect, 0, 5);
        Button rotatoPotato = new Button("Rotato Potato");
        GridPane.setConstraints(rotatoPotato,0, 6);
        GridPane.setConstraints(sepiaTone,0, 7);
        Button blurButton = new Button("Blur Image");
        GridPane.setConstraints(blurButton, 0, 8);
        Button brightButton = new Button("Brighten Image");
        GridPane.setConstraints(brightButton, 0, 9);
        Button darkButton = new Button("Darken Image");
        GridPane.setConstraints(darkButton, 0, 10);
        Button rightButton = new Button(">> Right >>");
        GridPane.setConstraints(navilbl, 0, 11);
        navilbl.setAlignment(Pos.CENTER);
        GridPane.setConstraints(rightButton,0, 12);
        Button leftButton = new Button("<< Left <<");
        GridPane.setConstraints(leftButton , 0, 13);
        GridPane.setConstraints(lbl, 0, 16);
        lbl.setAlignment(Pos.CENTER);
        
        fileButton.setMaxWidth(Double.MAX_VALUE);
        rotatoPotato.setMaxWidth(Double.MAX_VALUE);
        reflect.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        sepiaTone.setMaxWidth(Double.MAX_VALUE);
        leftButton.setMaxWidth(Double.MAX_VALUE);
        rightButton.setMaxWidth(Double.MAX_VALUE);
        lbl.setMaxWidth(Double.MAX_VALUE);
        fxlbl.setMaxWidth(Double.MAX_VALUE);
        toplbl.setMaxWidth(Double.MAX_VALUE);
        navilbl.setMaxWidth(Double.MAX_VALUE);
        blurButton.setMaxWidth(Double.MAX_VALUE);
        brightButton.setMaxWidth(Double.MAX_VALUE);
        darkButton.setMaxWidth(Double.MAX_VALUE);
        
        sepiaTone.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent event) {
            	
            	 if ("Apply Sepia".equals(sepiaTone.getText())) {
            		 myImageView.setEffect(new SepiaTone());
                 	sepiaTone.setText("Remove Effects");
                 	blurButton.setText("Remove Effects");
                 	brightButton.setText("Remove Effects");
                 	darkButton.setText("Remove Effects");
                 } else {
                	 myImageView.setEffect(null);
                	 sepiaTone.setText("Apply Sepia");
                	 blurButton.setText("Blur Image");
                	 brightButton.setText("Brighten Image");
                	 darkButton.setText("Darken Image");
                 }           
            }});
        
        blurButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
        	
       	 if ("Blur Image".equals(blurButton.getText())) {
       		 myImageView.setEffect(new GaussianBlur());
            	blurButton.setText("Remove Effects");
            	sepiaTone.setText("Remove Effects");
            	brightButton.setText("Remove Effects");
            	darkButton.setText("Remove Effects");
            } else {
           	 myImageView.setEffect(null);
           	 sepiaTone.setText("Apply Sepia");
           	 blurButton.setText("Blur Image");
           	brightButton.setText("Brighten Image");
           	darkButton.setText("Darken Image");
            }           
       }});
       
        brightButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
           	 if ("Brighten Image".equals(brightButton.getText())) {
           		ColorAdjust colorAdjust = new ColorAdjust();
           		colorAdjust.setBrightness(+0.8);
           		myImageView.setEffect(colorAdjust);
           			brightButton.setText("Remove Effects");
                	blurButton.setText("Remove Effects");
                	sepiaTone.setText("Remove Effects");
                	darkButton.setText("Remove Effects");
                } else {
               	 myImageView.setEffect(null);
               	 sepiaTone.setText("Apply Sepia");
               	 blurButton.setText("Blur Image");
               	 brightButton.setText("Brighten Image");
               	darkButton.setText("Darken Image");
                }           
           }});
        
        darkButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	
           	 if ("Darken Image".equals(darkButton.getText())) {
           		ColorAdjust colorAdjust = new ColorAdjust();
           		colorAdjust.setBrightness(-0.8);
           		myImageView.setEffect(colorAdjust);
           			brightButton.setText("Remove Effects");
                	blurButton.setText("Remove Effects");
                	sepiaTone.setText("Remove Effects");
                	darkButton.setText("Remove Effects");
                	
                } else {
               	 myImageView.setEffect(null);
               	 sepiaTone.setText("Apply Sepia");
               	 blurButton.setText("Blur Image");
               	 brightButton.setText("Brighten Image");
               	 darkButton.setText("Darken Image");
                }           
           }});
        
       
        rotatoPotato.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent event) {
              rotate();
              
            }});
        
        	rightButton.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent event) {
            	nextButton();
              
            }});
        	
        	leftButton.setOnAction(new EventHandler<ActionEvent>() {
             	 
                @Override
                public void handle(ActionEvent event) {
                	previousButton();
                  
                }});
        
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
              openFile();
              
            }});
 
        reflect.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
               myImageView.setRotationAxis(Rotate.Y_AXIS);
               degreevalue +=180;
               myImageView.setRotate(degreevalue);
//              
            }
        });
      
       saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                
                File file = fileChooser.showSaveDialog(window);
                if (file != null) {
                    try {
                   	
                        ImageIO.write(SwingFXUtils.fromFXImage(myImageView.getImage(),
                                null), "jpg", file);
                 
                    } catch (IOException ex) {
                        Logger.getLogger(
                            testfivehundredthirtytwo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
       MenuItem menuItem2 = new MenuItem("Features");
       menuItem2.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
        	   final Stage dialog = new Stage();
               dialog.initModality(Modality.APPLICATION_MODAL);
               dialog.initOwner(primaryStage);
               VBox dialogVbox = new VBox(); 
               dialogVbox.setAlignment(Pos.CENTER);
               Text t = new Text("Use scroll wheel to zoom in and out like a g6" + "\n\n\n");
               t.setFont(new Font(12));
               dialogVbox.getChildren().add(t);
               Scene dialogScene = new Scene(dialogVbox, 300, 200);
               dialog.setScene(dialogScene);
               dialog.show();
           }
       });
       MenuItem menuItem3 = new MenuItem("About");
       menuItem3.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent e) {
        	   final Stage dialog = new Stage();
               dialog.initModality(Modality.APPLICATION_MODAL);
               dialog.initOwner(primaryStage);
               VBox dialogVbox = new VBox(20);
               dialogVbox.setAlignment(Pos.CENTER);
               Text t = new Text("This application is made for non-comercial use only, please purchase lisence" + "\n\n\n");
               t.setFont(new Font(12));
               t.setWrappingWidth(200);
               t.setTextAlignment(TextAlignment.CENTER);
               dialogVbox.getChildren().add(t);
               Scene dialogScene = new Scene(dialogVbox, 300, 200);
               dialog.setScene(dialogScene);
               dialog.show();
           }
       });
       
        MenuItem menuItem = new MenuItem("Open Image");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	openFile();
            }
        });
        MenuItem menuItem1 = new MenuItem("Close");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                close();
            }
        });
        final Menu menu1 = new Menu("File");
        menu1.getItems().addAll(menuItem, menuItem1);
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        menu3.getItems().addAll(menuItem2, menuItem3);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3);
        
        HBox imageBox = new HBox();
        imageBox.getChildren().addAll(myImageView,scrollPane); 
        scrollPane.setMinWidth(2000);
        
        BorderPane borderPane = new BorderPane();
        //Adds everything to grid
        grid.getChildren().addAll(fxlbl,toplbl,lbl, sepiaTone, navilbl, darkButton, fileButton,reflect, rotatoPotato, saveButton, rightButton, leftButton, blurButton, brightButton);
        borderPane.setTop(menuBar);
    	borderPane.setLeft(grid);
    	borderPane.setCenter(imageBox);
    	
        
        Scene scene = new Scene(borderPane, 720, 540);
        scene.getStylesheets().add("viper.css");
        window.setScene(scene);
        window.show();
    }

    public void close(){
	Platform.exit();	
    }

    public void rotate()
        {	
    		
    	    myImageView.setRotationAxis(Rotate.Z_AXIS);
        	degreevalue += 90;
        	myImageView.setRotate(degreevalue);
        }
    
    public void openFile() {
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open file");
		
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg")
				);
		
		File file = fileChooser.showOpenDialog(window);
		
		if(file == null){
			return;
		}
		totalCounter++;
		
		
		String path = file.getAbsolutePath(); //get the path of the file
		
		//try to display the content of the file path
		try {
			InputStream inputStream = new FileInputStream(path);
			Image image = new Image(inputStream);
			for(int i=currentCounter;i<totalCounter;i++){
				images[i] = image;
			}

			currentCounter++;
			imageCounter = currentCounter -1;
			myImageView.setImage(images[currentCounter-1]);
			myImageView.setRotate(0);
			if (first == 1){
				lbl.setText("Image number: 1");}
			else
			{
				lbl.setText("Image number: " + first);}
			first++;
			
			
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot show image");
		}	
	}
    public void nextButton(){
		if(imageCounter+1<totalCounter){
		myImageView.setRotate(0);
		myImageView.setImage(images[++imageCounter]);
		System.out.println(imageCounter);
		String x = Integer.toString(imageCounter+1);
		lbl.setText("Image number: " + x);
		myImageView.setEffect(null);
		sepiaTone.setText("Apply Sepia");
		}
		else{
			System.out.println("No next picture!");
			
		}
	}
    
    public void previousButton(){
		if(imageCounter-1>=0){
		myImageView.setRotate(0);
		myImageView.setEffect(null);
		myImageView.setImage(images[--imageCounter]);
		System.out.println(imageCounter);
		sepiaTone.setText("Apply Sepia");
		String x = Integer.toString(imageCounter+1);
		lbl.setText("Image number: " + x);
		}
		else{ 
			System.out.println("No previous image!");
		}
	}
}
    
