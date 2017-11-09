package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.commands.PersonDeletedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class AddressPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String DEFAULT_LIGHT_PAGE = "default_light.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = ",+?dg=dbrw&newdg=1";
    public static final String UNIT_NUMBER_REGEX = "#\\d+-\\d+";

    private static final String FXML = "AddressPanel.fxml";
    private static final String DARK_MODE = "view/DarkTheme.css";

    private ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private Label addressLarge;

    public AddressPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    //@@author Gideonfu-reused
    /**
     * Loads the address in Google Maps of the selected Person
     * @param person
     */
    private void loadPersonAddress(ReadOnlyPerson person) {

        if (person.getAddress().value.equals("~")) {
            loadDefaultPage();
        } else {
            loadPage(GOOGLE_SEARCH_URL_PREFIX
                    + person.getAddress().value.replaceAll(" ", "+").replaceAll(UNIT_NUMBER_REGEX, "")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
        this.person = person;
        bindListeners(person);
    }

    //@@author frozventus
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        loadDefaultPage();
        addressLarge.textProperty().unbind();
        addressLarge.textProperty().setValue("");
    }

    //@@author frozventus
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        addressLarge.textProperty().bind(Bindings.convert(person.addressProperty()));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }
    //@@author darrinloh

    public void setDefaultPage() {
        Scene scene = MainWindow.getScene();
        if (scene.getStylesheets().contains(DARK_MODE)) {
            URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
            loadPage(defaultPage.toExternalForm());
        } else {
            URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_LIGHT_PAGE);
            loadPage(defaultPage.toExternalForm());
        }
    }

    //@@author darrinloh
    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonAddress(event.getNewSelection().person);
    }

    //@@author frozventus
    @Subscribe
    public void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
