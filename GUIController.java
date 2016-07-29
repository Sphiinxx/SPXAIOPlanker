package scripts.SPXAIOPlanker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tribot.api.Timing;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.data.enums.PlankType;
import scripts.TribotAPI.Client;
import scripts.TribotAPI.SendReportData;
import scripts.TribotAPI.gui.AbstractGUIController;
import scripts.TribotAPI.util.Utility;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class GUIController extends AbstractGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button start;

    @FXML
    private ComboBox<PlankType> plank_type;

    @FXML
    private Spinner<Integer> coins_to_take;

    @FXML
    private TextArea bug_clientdebug;

    @FXML
    private TextArea bug_stacktrace;

    @FXML
    private TextArea bug_description;

    @FXML
    private TextArea bug_botdebug;

    @FXML
    private Button send_report;

    @FXML
    private Label report_sent;

    @FXML
    private Label report_spam;

    @FXML
    private Hyperlink join_discord;

    @FXML
    private Hyperlink add_skype;

    @FXML
    private Hyperlink private_message;

    @FXML
    private Hyperlink website_link;

    @FXML
    void initialize() {
        plank_type.getItems().setAll(PlankType.values());

        plank_type.getSelectionModel().select(0);

        join_discord.setOnAction((event) -> Utility.openURL("https://discordapp.com/invite/0yCbdv5qTOWmxUD5"));

        add_skype.setOnAction((event -> Utility.openURL("http://hatscripts.com/addskype/?sphiin.x")));

        private_message.setOnAction((event -> Utility.openURL("https://tribot.org/forums/profile/176138-sphiinx/")));

        website_link.setOnAction((event -> Utility.openURL("http://spxscripts.com/")));

        send_report.setOnAction((event) -> {
            if (SendReportData.LAST_SENT_TIME <= 0 || Timing.timeFromMark(SendReportData.LAST_SENT_TIME) > 60000) {
                if (!SendReportData.sendReportData(Client.getManifest(Main.class).name(), Client.getManifest(Main.class).version(), bug_description.getText(), bug_stacktrace.getText(), bug_clientdebug.getText(), bug_botdebug.getText()))
                    report_sent.setText("UH OH! There seems to have been an error with your report!");

                report_sent.setOpacity(1);
                report_spam.setOpacity(0);
                SendReportData.LAST_SENT_TIME = Timing.currentTimeMillis();
            } else {
                report_sent.setOpacity(0);
                report_spam.setOpacity(1);
            }
        });

        start.setOnAction((event) -> {
            Vars.get().coins_to_take = Integer.parseInt(coins_to_take.getValue().toString());
            Vars.get().plank_type = plank_type.getSelectionModel().getSelectedItem();
            getGUI().close();
        });
    }
}

