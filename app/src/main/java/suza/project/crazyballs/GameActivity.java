package suza.project.crazyballs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import suza.project.crazyballs.game.GamePanel;
import suza.project.crazyballs.state.BasketGameState;
import suza.project.crazyballs.util.IGameFinishedListener;
import suza.project.crazyballs.util.IGamePausedListener;

/**
 * This class represents the main game activity. It sets the game panel
 * reference as a content view.
 */
public class GameActivity extends AppCompatActivity {

    private static final String TAG = GameActivity.class.getSimpleName();
    public static final String SCORE_KEY = "score";

    private GamePanel gamePanel;

    /**
     * Game finished listener.
     */
    private IGameFinishedListener finishedListener = new IGameFinishedListener() {

        @Override
        public void gameFinished(int score) {
            // Game is finished, do all the final things here
            Log.d(TAG, "Listener - Finishing the activity.");

            // Send score
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra(GameActivity.SCORE_KEY, score);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            GameActivity.this.startActivity(intent);
            GameActivity.this.finish();
            gamePanel.finish(score);
        }
    };

    /**
     * Game paused listener.
     */
    private IGamePausedListener pauseListener = new IGamePausedListener() {

        @Override
        public void gamePaused() {
            // Game is paused, display dialog
            Log.d(TAG, "Listener - Pausing the activity.");
            gamePanel.pause();
            // Make finishing alert dialog
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(GameActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog);
            } else {
                builder = new AlertDialog.Builder(GameActivity.this);
            }
            // Configure builder
            builder.setTitle("GAME PAUSED")
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            GameActivity.this.startActivity(intent);
                            GameActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            gamePanel.unpause();
                        }
                    })
                    .setCancelable(false);

            // Create and show dialog
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Turn off the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Full screen application
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gamePanel = new GamePanel(this);
        BasketGameState basketGameState = new BasketGameState(gamePanel);
        basketGameState.setGamePausedListener(pauseListener);
        basketGameState.setGameFinishedListener(finishedListener);
        gamePanel.setGameState(basketGameState);

        // Set Game panel as the view
        setContentView(gamePanel);
        Log.d(TAG, "Game surface created.");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping main view...");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying main view...");
        super.onDestroy();
    }
}
