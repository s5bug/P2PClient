package com.tsunderebug.p2pclient.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class Message extends VBox {

	@FXML
	private HBox member;
	@FXML
	private Label mname;
	@FXML
	private Label mip;
	@FXML
	private TextFlow content;

	public static final Text ORIGINAL = new Text();

	static {
		ORIGINAL.setFont(Font.font("Roboto", 14));
	}

	public Message() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(
						"/fxml/control/message.fxml"
				)
		);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
