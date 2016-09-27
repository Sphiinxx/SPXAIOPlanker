package scripts.spxaioplanker;

import java.net.URL;
import java.util.ResourceBundle;

import com.allatori.annotations.DoNotRename;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tribot.api.Timing;
import scripts.spxaioplanker.data.Vars;
import scripts.spxaioplanker.data.enums.PlankType;
import scripts.tribotapi.Client;
import scripts.tribotapi.PostRequest;
import scripts.tribotapi.gui.AbstractGUIController;
import scripts.tribotapi.util.Utility;

/**
 * Created by Sphiinx on 7/28/2016.
 */
@DoNotRename
public class GUIController extends AbstractGUIController {

    @FXML
    @DoNotRename
    private ResourceBundle resources;

    @FXML
    @DoNotRename
    private URL location;

    @FXML
    @DoNotRename
    private Button start;

    @FXML
    @DoNotRename
    private ComboBox<PlankType> plank_type;

    @FXML
    @DoNotRename
    private Spinner<Double> coins_to_take;

    @FXML
    @DoNotRename
    private TextArea bug_clientdebug;

    @FXML
    @DoNotRename
    private TextArea bug_stacktrace;

    @FXML
    @DoNotRename
    private TextArea bug_description;

    @FXML
    @DoNotRename
    private TextArea bug_botdebug;

    @FXML
    @DoNotRename
    private Button send_report;

    @FXML
    @DoNotRename
    private Label report_sent;

    @FXML
    @DoNotRename
    private Label report_spam;

    @FXML
    @DoNotRename
    private Hyperlink join_discord;

    @FXML
    @DoNotRename
    private Hyperlink add_skype;

    @FXML
    @DoNotRename
    private Hyperlink private_message;

    @FXML
    @DoNotRename
    private Hyperlink website_link;

    @FXML
    @DoNotRename
    void initialize() {
        plank_type.getItems().setAll(PlankType.values());
        plank_type.getSelectionModel().select(0);

        join_discord.setOnAction((event) -> Utility.openURL("https://discordapp.com/invite/0yCbdv5qTOWmxUD5"));

        add_skype.setOnAction((event -> Utility.openURL("http://hatscripts.com/addskype/?sphiin.x")));

        private_message.setOnAction((event -> Utility.openURL("https://tribot.org/forums/profile/176138-sphiinx/")));

        website_link.setOnAction((event -> Utility.openURL("http://spxscripts.com/")));

        send_report.setOnAction((event) -> {
            if (PostRequest.LAST_SENT_TIME <= 0 || Timing.timeFromMark(PostRequest.LAST_SENT_TIME) > 60000) {
                if (!PostRequest.sendReportData(Client.getManifest(Main.class).name(), Client.getManifest(Main.class).version(), bug_description.getText(), bug_stacktrace.getText(), bug_clientdebug.getText(), bug_botdebug.getText()))
                    report_sent.setText("UH OH! There seems to have been an error with your report!");

                report_sent.setOpacity(1);
                report_spam.setOpacity(0);
                PostRequest.LAST_SENT_TIME = Timing.currentTimeMillis();
            } else {
                report_sent.setOpacity(0);
                report_spam.setOpacity(1);
            }
        });

        start.setOnAction((event) -> {
            Vars.get().coins_to_take = Double.parseDouble(coins_to_take.getValue().toString());
            Vars.get().plank_type = plank_type.getSelectionModel().getSelectedItem();
            getGUI().close();
        });
    }
}

