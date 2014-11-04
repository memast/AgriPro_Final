package com.agripro.gwt.client;
 
import com.agripro.gwt.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
 //Test
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AgriPro implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
 
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
 
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Add Selection Buttons
        final Button evaluationImportExportButton = new Button("Import & Export");
        RootPanel.get("evaluation-button-container").add(evaluationImportExportButton);
        final Button evaluationProductionButton = new Button("Produktion");
        RootPanel.get("evaluation-button-container").add(evaluationProductionButton);
        final Button evaluationPopulationButton = new Button("Bevoelkerung");
        RootPanel.get("evaluation-button-container").add(evaluationPopulationButton);
         
         
        // Add Visualization Buttons
        final Button visualizationTableButton = new Button("Tabelle");
        RootPanel.get("visualization-button-container").add(visualizationTableButton);
        final Button visualizationMapButton = new Button("Karte");
        RootPanel.get("visualization-button-container").add(visualizationMapButton);
         
    }
}