package com.antiso.jpwgen.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class JpwgenUI implements EntryPoint {

	public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("mainPanel");
		mainPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(5);
		mainPanel.add(verticalPanel, 10, 10);
		verticalPanel.setSize("89%", "");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("40%", "100%");

		Label lblSeed = new Label("Seed");
		verticalPanel_1.add(lblSeed);

		TextArea seedText = new TextArea();
		verticalPanel_1.add(seedText);
		seedText.setSize("194px", "80px");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_2);
		verticalPanel_2.setSize("40%", "100%");

		Label lblComment = new Label("Comment");
		verticalPanel_2.add(lblComment);

		TextArea commentText = new TextArea();
		verticalPanel_2.add(commentText);
		commentText.setSize("194px", "80px");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setStyleName("buttonPanel");
		verticalPanel_3.setSpacing(2);
		horizontalPanel.add(verticalPanel_3);
		verticalPanel_3.setWidth("20%");

		Button btnGenerate = new Button("Generate");
		verticalPanel_3.add(btnGenerate);

		Button btnSave = new Button("Save");
		verticalPanel_3.add(btnSave);

		Button btnLoad = new Button("Load");
		verticalPanel_3.add(btnLoad);

		DecoratorPanel decoratorPanel = new DecoratorPanel();
		verticalPanel.add(decoratorPanel);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(5);
		decoratorPanel.setWidget(horizontalPanel_1);

		SimpleCheckBox simpleCheckBox = new SimpleCheckBox();
		horizontalPanel_1.add(simpleCheckBox);

		Label lblIncludeSymbols = new Label("Include symbols");
		horizontalPanel_1.add(lblIncludeSymbols);

		SimpleCheckBox includeNumbersBox = new SimpleCheckBox();
		horizontalPanel_1.add(includeNumbersBox);

		Label lblIncludeNumbers = new Label("Include numbers");
		horizontalPanel_1.add(lblIncludeNumbers);

		InlineHTML inlineHTML = new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;");
		horizontalPanel_1.add(inlineHTML);
		horizontalPanel_1.add(inlineHTML);

		horizontalPanel_1.add(inlineHTML);

		IntegerBox passwordLenghtBox = new IntegerBox();
		horizontalPanel_1.add(passwordLenghtBox);
		passwordLenghtBox.setWidth("43px");

		Label lblPasswordLength = new Label("Password length");
		horizontalPanel_1.add(lblPasswordLength);

		InlineHTML inlineHTML_1 = new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;");
		horizontalPanel_1.add(inlineHTML_1);

		IntegerBox startPasswordBox = new IntegerBox();
		startPasswordBox.setStyleName("withSpacer");
		horizontalPanel_1.add(startPasswordBox);
		startPasswordBox.setWidth("43px");

		Label lblNewLabel = new Label("Passwords count");
		horizontalPanel_1.add(lblNewLabel);

		CellTable<String[]> passwordsTable = new CellTable<String[]>();
		passwordsTable.setPageSize(25);
		InlineHTML tmpCell = new InlineHTML("999sadfdaf");
		verticalPanel.add(tmpCell);
		int columnsCount = 580 / (tmpCell.getOffsetWidth()+34);
		tmpCell.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		verticalPanel.remove(tmpCell);
		for (int i = 0; i < columnsCount; i++) {
			final int tmp = i;
			passwordsTable.addColumn(new TextColumn<String[]>() {
				final int index = tmp;
				@Override
				public String getValue(String[] object) {
					return object[index];
				}

			}, tmp==0 ? "№": String.valueOf(tmp-1));
		}
		passwordsTable.addColumnStyleName(0, "rightBordered");
		passwordsTable.getColumn(0).setCellStyleNames("rightBordered");
		passwordsTable.getHeader(0).setHeaderStyleNames("rightBordered");
		int value=0;
		ListDataProvider<String[]> data = new ListDataProvider<String[]>();
		for (int i = 0; i < 100; i++) {
			String[] values=new String[columnsCount];
			values[0]=String.valueOf(columnsCount*i);
			for (int j=value;j<value+columnsCount-1;j++) {
				values[j-value+1]=String.valueOf(j);
			}
			data.getList().add(values);
			value+=columnsCount;
		}
		data.addDataDisplay(passwordsTable);
		verticalPanel.add(passwordsTable);
		passwordsTable.setWidth("100%");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		SimplePager pager = new SimplePager(TextLocation.CENTER,
				pagerResources, false, 0, true);
		pager.setDisplay(passwordsTable);

		// SimplePager simplePager = new SimplePager();
		// simplePager.setDisplay(passwordsTable);
		verticalPanel.add(pager);
		verticalPanel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
		// simplePager.setVisible(false);

	}
}
