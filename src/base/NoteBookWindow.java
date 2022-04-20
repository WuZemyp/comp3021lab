package base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * NoteBook GUI with JAVAFX
 *
 * COMP 3021
 *
 *
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 *
	 * Combobox for selecting the folder
	 *
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		this.stage=stage;
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 *
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);
		Button buttonSave = new Button("Save to File");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		Label searchLabel=new Label("Search :");
		//searchLabel.setPrefSize(100, 20);
		TextField t=new TextField();
		Button buttonSearch = new Button("Search");
		buttonSearch.setPrefSize(100, 20);
		//buttonSearch.setDisable(true);
		Button buttonClear = new Button("Clear Search");
		buttonClear.setPrefSize(100, 20);
		//buttonClear.setDisable(true);
		Button buttonsave=new Button("Save Note");
		buttonsave.setPrefSize(100, 20);
		buttonsave.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(foldersComboBox.getSelectionModel().getSelectedItem().equals("-----")||titleslistView.getSelectionModel().getSelectedItem()==null)
						{
							Alert alert = new Alert(AlertType.WARNING);
							 alert.setTitle("Warning");
							 alert.setContentText("Please select a folder and a note");
							 alert.showAndWait().ifPresent(rs -> {
							  if (rs == ButtonType.OK) {
							  System.out.println("Pressed OK.");
							  }
							 });
							 return;
						}
						String content=textAreaNote.getText();
						for(Folder f:noteBook.getFolders())
						{
							if(f.getName().equals(foldersComboBox.getSelectionModel().getSelectedItem()))
							{
								for(Note n:f.getNotes())
								{
									if(n.getTitle().equals(titleslistView.getSelectionModel().getSelectedItem()))
									{
										TextNote tn=(TextNote)n;
										tn.setContent(content);
										return;
									}
								}
							}
						}
					}

				});

		Button buttondelete=new Button("Delete Note");
		try{
			Image image = new Image(new FileInputStream("save.png"));
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			buttonsave.setGraphic(imageView);
		}catch(Exception e)
		{

		}
		try{
			Image image = new Image(new FileInputStream("delete.png"));
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			buttondelete.setGraphic(imageView);
		}catch(Exception e)
		{

		}

		buttondelete.setPrefSize(110, 20);
		buttondelete.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(foldersComboBox.getSelectionModel().getSelectedItem().equals("-----")||titleslistView.getSelectionModel().getSelectedItem()==null)
				{
					Alert alert = new Alert(AlertType.WARNING);
					 alert.setTitle("Warning");
					 alert.setContentText("Please select a folder and a note");
					 alert.showAndWait().ifPresent(rs -> {
					  if (rs == ButtonType.OK) {
					  System.out.println("Pressed OK.");
					  }
					 });
					 return;
				}
				for(Folder f:noteBook.getFolders())
				{
					if(f.getName().equals(foldersComboBox.getSelectionModel().getSelectedItem()))
					{
						if(f.removeNotes(titleslistView.getSelectionModel().getSelectedItem()))
						{
							 Alert alert = new Alert(AlertType.CONFIRMATION);
							 alert.setTitle("Succeed");
							 alert.setContentText("Your note has been successfully removed");
							 alert.showAndWait().ifPresent(rs -> {
							  if (rs == ButtonType.OK) {
							  System.out.println("Pressed OK.");
							  }
							 });
							 updateListView();
							 return;
						}
					}
				}
			}

		});
		HBox hbox1=new HBox();
		hbox1.setSpacing(10);
		hbox1.getChildren().addAll(buttonsave,buttondelete);
		VBox vbox=new VBox();
		vbox.setSpacing(10);
		vbox.setSpacing(10);
		vbox.getChildren().addAll(t,hbox1);
		hbox.getChildren().addAll(buttonLoad, buttonSave,searchLabel,vbox,buttonSearch,buttonClear);
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			currentSearch = t.getText();
			textAreaNote.setText("");
			List<Note> notes;
			ArrayList<String> list = new ArrayList<String>();
			for(Folder f:noteBook.getFolders())
			{
				if(f.getName().equals(currentFolder))
				{
					notes=f.searchNotes(currentSearch);
					for(Note n:notes)
					{
						list.add(n.getTitle());
					}

					break;
				}
			}

			ObservableList<String> combox2 = FXCollections.observableArrayList(list);
			titleslistView.setItems(combox2);



			}
			});
		buttonClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			currentSearch = "";
			t.setText("");
			textAreaNote.setText("");
			updateListView();
			}
			});
		buttonLoad.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub\
				FileChooser fileChooser=new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				FileChooser.ExtensionFilter extFilter=new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				File file =fileChooser.showOpenDialog(stage);
				if (file!=null)
				{
					loadNoteBook(file);
				}

			}

		});
		buttonSave.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FileChooser fileChooser=new FileChooser();
				fileChooser.setTitle("Please Choose An File To Save!");
				FileChooser.ExtensionFilter extFilter=new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				File file =fileChooser.showOpenDialog(stage);
				if (file!=null)
				{
					if(noteBook.save(file.getName()))
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successfully saved");
						alert.setContentText("You file has been saved to file "
						+ file.getName());
						alert.showAndWait().ifPresent(rs -> {
						 if (rs == ButtonType.OK) {
						 System.out.println("Pressed OK.");
						 }
						});

					}
				}


			}

		});



		return hbox;
	}

	/**
	 * this create the section on the left
	 *
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		//foldersComboBox.getItems().addAll("FOLDER NAME 1", "FOLDER NAME 2", "FOLDER NAME 3");
		ArrayList<Folder> folders=noteBook.getFolders();
		for(Folder f:folders)
		{
			foldersComboBox.getItems().addAll(f.getName());
		}

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");
		Button buttonAdd=new Button("Add a Folder");
		buttonAdd.setPrefSize(100, 20);
		Button buttonAddn=new Button("Add a Note");
		buttonAddn.setPrefSize(100, 20);
		HBox hbox=new HBox();
		//hbox.setPadding(new Insets(10));
		buttonAdd.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						TextInputDialog dialog = new TextInputDialog("Add a Folder");
						 dialog.setTitle("Input");
						 dialog.setHeaderText("Add a new folder for your notebook:");
						 dialog.setContentText("Please enter the name you want to create:");
						 // Traditional way to get the response value.
						 Optional<String> result = dialog.showAndWait();
						 if (result.isPresent()){
						 // TODO
							 String name=result.get();
							 if(name.equals(null)||name.equals(""))
							 {
								 Alert alert = new Alert(AlertType.WARNING);
								 alert.setTitle("Warning");
								 alert.setContentText("Please input an valid folder name");
								 alert.showAndWait().ifPresent(rs -> {
								  if (rs == ButtonType.OK) {
								  System.out.println("Pressed OK.");
								  }
								 });
								 return;

							 }
							 for(Folder f:noteBook.getFolders())
							 {
								 if(f.getName().equals(name))
								 {
									 Alert alert = new Alert(AlertType.WARNING);
									 alert.setTitle("Warning");
									 alert.setContentText("You already have a folder named with "+name);
									 alert.showAndWait().ifPresent(rs -> {
									  if (rs == ButtonType.OK) {
									  System.out.println("Pressed OK.");
									  }
									 });
									 return;
								 }

							 }
							 noteBook.addFolder(name);
							 foldersComboBox.getItems().addAll(name);
							 foldersComboBox.getSelectionModel().select(name);
						 }

					}

				});
		buttonAddn.setOnAction(new EventHandler<ActionEvent>()
				{

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(foldersComboBox.getSelectionModel().getSelectedItem().equals("-----")||foldersComboBox.getSelectionModel().getSelectedItem().equals(null))
						{
							Alert alert = new Alert(AlertType.WARNING);
							 alert.setTitle("Warning");
							 alert.setContentText("Please choose a folder first!");
							 alert.showAndWait().ifPresent(rs -> {
							  if (rs == ButtonType.OK) {
							  System.out.println("Pressed OK.");
							  }
							 });
							 return;
						}
						TextInputDialog dialog = new TextInputDialog("Add a new note to current folder");
						 dialog.setTitle("Input");
						 dialog.setHeaderText("Add a new folder for your notebook:");
						 dialog.setContentText("Please enter the name of your note:");
						 // Traditional way to get the response value.
						 Optional<String> result = dialog.showAndWait();
						 if(result.get()==null||result.get().equals(""))
						 {
							 return;
						 }
						 if(result.isPresent())
						 {

							 noteBook.createTextNote(currentFolder, result.get());
						 }
						 Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Successful");
							alert.setContentText("Insert note "+result.get()+" to folder "+currentFolder+" successfully!");
							alert.showAndWait().ifPresent(rs -> {
							 if (rs == ButtonType.OK) {
							 System.out.println("Pressed OK.");
							 }
							});
						 updateListView();

					}

				});
		hbox.setSpacing(8);
		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(buttonAdd);
		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote

				String content = "";
				for(Folder f:noteBook.getFolders())
				{
					if(f.getName().equals(currentFolder))
					{

						for(Note n:f.getNotes())
						{
							if(n.getTitle().equals(title))
							{
								content=((TextNote)n).getcontent();
								break;
							}
						}
					}

				}
				textAreaNote.setText(content);

			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(buttonAddn);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder

		for(Folder f:noteBook.getFolders())
		{
			if(f.getName().equals(currentFolder))
			{

				for(Note n:f.getNotes())
				{
					if(n instanceof TextNote)
					{
						list.add(n.getTitle());

					}
				}
				break;
			}
		}



		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 0);

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called ¡°the most shocking play in NFL history¡± and the Washington Redskins dubbed the ¡°Throwback Special¡±: the November 1985 play in which the Redskins¡¯ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award¨Cwinning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything¡ªuntil it wasn¡¯t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant¡ªa part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether¡¯s Daddy Was a Number Runner and Dorothy Allison¡¯s Bastard Out of Carolina, Jacqueline Woodson¡¯s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood¡ªthe promise and peril of growing up¡ªand exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	private void loadNoteBook(File file){
		NoteBook nb=new NoteBook(file.getName());
		for(Folder f:noteBook.getFolders())
		{
			foldersComboBox.getItems().remove(f.getName());
		}
		for(Folder f:nb.getFolders())
		{
			foldersComboBox.getItems().addAll(f.getName());
		}
		foldersComboBox.setValue("-----");
		noteBook=nb;

	}

}