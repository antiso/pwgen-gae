package com.antiso.jpwgen.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class JpwgenUI implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);
	private final PwgenServiceAsync pwgenServiceAsync = PwgenServiceAsync.Util
			.getInstance();
	private CellTable<String[]> passwordsTable;
	private VerticalPanel verticalPanel;
	private ListDataProvider<String[]> data;
	private SimplePager pager;
	private TextArea seedText;
	private SimpleCheckBox includeSymbolsBox;
	private SimpleCheckBox includeNumbersBox;
	private IntegerBox passwordLenghtBox;
	private IntegerBox passwordsCount;
    private int charWidth;
    Logger log = Logger.getLogger("jpwgen");

    public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("mainPanel");
		mainPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(5);
		mainPanel.add(verticalPanel, 10, 10);
		verticalPanel.setSize("89%", "");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("40%", "100%");

		Label lblSeed = new Label(messages.lblSeed());
		verticalPanel_1.add(lblSeed);

		seedText = new TextArea();
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
		btnGenerate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				generatePasswords();
			}
		});
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

		includeSymbolsBox = new SimpleCheckBox();
		includeSymbolsBox.setValue(true);
		horizontalPanel_1.add(includeSymbolsBox);

		Label lblIncludeSymbols = new Label("Include symbols");
		horizontalPanel_1.add(lblIncludeSymbols);

		includeNumbersBox = new SimpleCheckBox();
		includeNumbersBox.setValue(true);
		horizontalPanel_1.add(includeNumbersBox);

		Label lblIncludeNumbers = new Label("Include numbers");
		horizontalPanel_1.add(lblIncludeNumbers);

		InlineHTML inlineHTML = new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;");
		horizontalPanel_1.add(inlineHTML);
		horizontalPanel_1.add(inlineHTML);

		horizontalPanel_1.add(inlineHTML);

		passwordLenghtBox = new IntegerBox();
		passwordLenghtBox.setValue(8);
		horizontalPanel_1.add(passwordLenghtBox);
		passwordLenghtBox.setWidth("43px");

		Label lblPasswordLength = new Label("Password length");
		horizontalPanel_1.add(lblPasswordLength);

		InlineHTML inlineHTML_1 = new InlineHTML("&nbsp;&nbsp;&nbsp;&nbsp;");
		horizontalPanel_1.add(inlineHTML_1);

		passwordsCount = new IntegerBox();
		passwordsCount.setValue(100);
		passwordsCount.setStyleName("withSpacer");
		horizontalPanel_1.add(passwordsCount);
		passwordsCount.setWidth("43px");

		Label lblNewLabel = new Label("Passwords count");
		horizontalPanel_1.add(lblNewLabel);

		passwordsTable = new CellTable<String[]>();
		passwordsTable.setPageSize(16);
		data = new ListDataProvider<String[]>();
		data.addDataDisplay(passwordsTable);
		verticalPanel.add(passwordsTable);
		passwordsTable.setWidth("100%");

		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(passwordsTable);

		// SimplePager simplePager = new SimplePager();
		// simplePager.setDisplay(passwordsTable);
		verticalPanel.add(pager);
		verticalPanel.setCellHorizontalAlignment(pager,
				HasHorizontalAlignment.ALIGN_CENTER);
        Element testEl =  DOM.getElementById("testcell");
        charWidth = (testEl.getOffsetWidth()-2)/8;
        log.info("Charwidth: " + charWidth);
        testEl.setAttribute("hidden","true");
        pager.setVisible(false);

	}

	private void generatePasswords() {
		int columnCount = passwordsTable.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			passwordsTable.removeColumn(0);
		}
		ArrayList<String> options = new ArrayList<String>();
		if (seedText.getValue().trim().length() > 0) {
			options.add("-H");
			options.add(seedText.getValue().trim());
		}
		if (includeSymbolsBox.getValue() == false)
			options.add("-Y");
		if (includeNumbersBox.getValue() == false)
			options.add("-O");
		if (passwordLenghtBox.getValue() != null) {
			options.add("-s");
			options.add(passwordLenghtBox.getValue().toString());
		}
		if (passwordsCount.getValue() != null) {
			options.add("-N");
			options.add(passwordsCount.getValue().toString());
		}

		pwgenServiceAsync.generatePasswords(options.toArray(new String[] {}),
				new AsyncCallback<String[]>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(String[] result) {

						InlineHTML tmpCell = new InlineHTML(result[0]);
						verticalPanel.add(tmpCell);
						int columnsCount = 560 / (charWidth*passwordLenghtBox.getValue() + 34/* border + padding*/);
                        log.info("Password cell width: " + charWidth*passwordLenghtBox.getValue());
						tmpCell.getElement().getStyle()
								.setBorderStyle(BorderStyle.SOLID);
						verticalPanel.remove(tmpCell);
						for (int i = 0; i < columnsCount && i < result.length; i++) {
							final int tmp = i;
							passwordsTable.addColumn(
									new TextColumn<String[]>() {
										final int index = tmp;

										@Override
										public String getValue(String[] object) {
											return object[index];
										}

									}, tmp == 0 ? "№" : String.valueOf(tmp - 1));
						}
                        for (int i=1;i<passwordsTable.getColumnCount();i++) {
                            passwordsTable.getColumn(i).setCellStyleNames("password");
                        }
						passwordsTable.addColumnStyleName(0, "rightBordered");
						passwordsTable.getColumn(0).setCellStyleNames(
								"rightBordered");
						passwordsTable.getHeader(0).setHeaderStyleNames(
								"rightBordered");
						int value = 0;
						ArrayList<String[]> dataList = new ArrayList<String[]>();
						for (int i = 0; i < result.length / (columnsCount-1) + 1; i++) {
							String[] values = new String[columnsCount];
							values[0] = String.valueOf((columnsCount-1) * i);
							for (int j = value; j < value + columnsCount - 1
									&& j < result.length; j++) {
								values[j - value + 1] = String
										.valueOf(result[j]);
							}
							dataList.add(values);
							value += (columnsCount-1);
						}
						data.setList(dataList);
						pager.setVisible(true);
						data.refresh();

					}

				});
	}
}
