package com.tsunderebug.p2pclient.control;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class Connection extends BorderPane {

	@FXML
	private Text nickname;
	@FXML
	private Text ip;

	private com.tsunderebug.p2pclient.client.Connection conn;

	public Connection() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(
						"/fxml/control/connection.fxml"
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

	public Connection(String nick, String ip) {
		this();
		setNickname(nick);
		setIp(ip);
		conn = com.tsunderebug.p2pclient.client.Connection.apply(new String[]{ip});
	}

	public String getNickname() {
		return nicknameProperty().get();
	}

	public void setNickname(String value) {
		nicknameProperty().set(value);
	}

	public StringProperty nicknameProperty() {
		return nickname.textProperty();
	}

	public String getIp() {
		return ipProperty().get();
	}

	public void setIp(String value) {
		ipProperty().set(value);
	}

	public StringProperty ipProperty() {
		return ip.textProperty();
	}

	public com.tsunderebug.p2pclient.client.Connection getConn() {
		return conn;
	}
}
