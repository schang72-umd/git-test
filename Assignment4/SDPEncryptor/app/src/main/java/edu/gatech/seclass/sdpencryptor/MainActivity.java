package edu.gatech.seclass.sdpencryptor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Alphabet: 0-9, a-z, A-Z
    private static final String ALPHA_NUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ALPHA_NUMERIC_SIZE = ALPHA_NUMERIC.length();
    private static final int[] COPRIME_TO_62 = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61};
    private static final HashMap<Character, Integer> charToIndex = new HashMap<>();
    private static final char[] indexToChar = new char[ALPHA_NUMERIC_SIZE];

    static {
        // Build maps for char <-> index
        for (int i = 0; i < ALPHA_NUMERIC_SIZE; i++) {
            char c = ALPHA_NUMERIC.charAt(i);
            charToIndex.put(c, i);
            indexToChar[i] = c;
        }
    }

    private EditText messageText, scaleInput, shiftInput;
    private TextView encodedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        messageText = findViewById(R.id.messageTextID);
        scaleInput = findViewById(R.id.scaleInputID);
        shiftInput = findViewById(R.id.shiftInputID);
        encodedText = findViewById(R.id.encodedTextID);
        Button encodeButton = findViewById(R.id.encodeButtonID);

        encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeMessage();
            }
        });
    }

    // Encode the message from the EditText messageText
    private void encodeMessage() {
        // Reset errors
        messageText.setError(null);
        scaleInput.setError(null);
        shiftInput.setError(null);

        String message = messageText.getText().toString();
        String scaleStr = scaleInput.getText().toString();
        String shiftStr = shiftInput.getText().toString();

        boolean valid = true;

        // Validate Message:
        // 1. not empty
        // 2. at least one digit or character in the message
        if (TextUtils.isEmpty(message) || !message.matches(".*[a-zA-Z0-9].*")) {
            messageText.setError("Invalid Message Text");
            valid = false;
        }

        // Validate Scale:
        // 1. not empty
        // 2. a number
        // 3. a co-prime to 62
        int scale = -1;
        if (TextUtils.isEmpty(scaleStr)) {
            scaleInput.setError("Invalid Scale Input");
            valid = false;
        } else {
            try {
                scale = Integer.parseInt(scaleStr);
                if (!isCoprimeTo62(scale)) {
                    scaleInput.setError("Invalid Scale Input");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                // The scaleInput is not an integer
                scaleInput.setError("Invalid Scale Input");
                valid = false;
            }
        }

        // Validate Shift:
        // 1. not empty
        // 2. a number
        // 3. between 1 (include) and 62 (excluded)
        int shift = -1;
        if (TextUtils.isEmpty(shiftStr)) {
            shiftInput.setError("Invalid Shift Input");
            valid = false;
        } else {
            try {
                shift = Integer.parseInt(shiftStr);
                if (shift < 1 || shift >= ALPHA_NUMERIC_SIZE) {
                    shiftInput.setError("Invalid Shift Input");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                shiftInput.setError("Invalid Shift Input");
                valid = false;
            }
        }

        // If any invalid input, clear output and return
        if (!valid) {
            encodedText.setText("");
            return;
        }

        // All inputs valid, perform encoding
        String encoded = encodeAffineCipher(message, scale, shift);
        encodedText.setText(encoded);
    }

    // Check if the num is a co-prime to 62
    private static boolean isCoprimeTo62(int num) {
        if (num >= ALPHA_NUMERIC_SIZE || num < 1) {
            return false;
        }
        for (int a : COPRIME_TO_62) {
            if (a == num) {
                return true;
            }
        }
        return false;
    }

    // Affine Cipher encoding as specified
    private static String encodeAffineCipher(String message, int a, int b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            Integer idx = charToIndex.get(c);
            if (idx != null) {
                // Alphanumeric: apply cipher
                int encodedIdx = (a * idx + b) % ALPHA_NUMERIC_SIZE;
                sb.append(indexToChar[encodedIdx]);
            } else {
                // Non-alphanumeric: leave unchanged
                sb.append(c);
            }
        }
        return sb.toString();
    }

}