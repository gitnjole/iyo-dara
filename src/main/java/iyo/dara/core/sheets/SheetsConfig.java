package iyo.dara.core.sheets;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class SheetsConfig {
    @Value("${path.google.credentials}")
    private String PATH_GOOGLE_CREDENTIALS;

    @Value("${sheets.service.account.name}")
    private String SHEETS_SERVICE_ACCOUNT_NAME;

    @Bean
    public Sheets sheets() throws Exception {
        InputStream credentialsStream = getClass().getResourceAsStream(PATH_GOOGLE_CREDENTIALS);

        var credentials = ServiceAccountCredentials.fromStream(credentialsStream)
                .createScoped(List.of(SheetsScopes.SPREADSHEETS));

        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var gsonFactory = GsonFactory.getDefaultInstance();

        return new Sheets.Builder(httpTransport, gsonFactory, new HttpCredentialsAdapter(credentials))
                .setApplicationName(SHEETS_SERVICE_ACCOUNT_NAME)
                .build();
    }
}
