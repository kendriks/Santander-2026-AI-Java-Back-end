package dio.budgeting.infrastructure.http;

import dio.budgeting.application.ListTransactionsByCategoryUseCase;
import dio.budgeting.application.PersistTransactionUseCase;
import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersistTransactionUseCase persistTransactionUseCase;

    @Mock
    private ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;

    @Mock
    private TranscriptionModel transcriptionModel;

    @Mock
    private TextToSpeechModel textToSpeechModel;

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ChatClient chatClient = org.mockito.Mockito.mock(ChatClient.class);
        when(chatClientBuilder.defaultSystem(any(String.class))).thenReturn(chatClientBuilder);
        when(chatClientBuilder.defaultTools(any(), any())).thenReturn(chatClientBuilder);
        when(chatClientBuilder.build()).thenReturn(chatClient);

        var controller = new TransactionController(
                persistTransactionUseCase,
                listTransactionsByCategoryUseCase,
                transcriptionModel,
                new ClassPathResource("prompts/system-message.st"),
                chatClientBuilder,
                textToSpeechModel
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldListTransactionsUsingQueryParam() throws Exception {
        when(listTransactionsByCategoryUseCase.execute(Category.GROCERIES))
                .thenReturn(List.of(new TransactionOutput("1", "Mercado", "GROCERIES", 123.45)));

        mockMvc.perform(get("/transactions")
                        .param("category", "GROCERIES"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectInvalidTransactionPayload() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"\",\"category\":\"GROCERIES\",\"amount\":-10}"))
                .andExpect(status().isBadRequest());
    }
}
