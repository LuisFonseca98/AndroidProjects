package dam_a45125.thematiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton slbIcon, scpIcon, portoIcon;
    private Button bSoma, bSub, bMult, bDiv, bCosseno, bSeno, bMod, bTangente,setButton,getButton;
    private Calculator calc;
    private AnimationDrawable animDraw,animDraw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slbIcon = (ImageButton) findViewById(R.id.imageButtonSLB);
        scpIcon = (ImageButton) findViewById(R.id.imageButtonSCP);
        portoIcon = (ImageButton) findViewById(R.id.imageButtonPorto);

        bSoma = (Button) this.findViewById(R.id.buttonSoma);
        bSub = (Button) this.findViewById(R.id.buttonSubtracao);
        bMult = (Button) this.findViewById(R.id.buttonMult);
        bDiv = (Button) this.findViewById(R.id.buttonDiv);
        bCosseno = (Button) this.findViewById(R.id.buttonCos);
        bSeno = (Button) this.findViewById(R.id.buttonSeno);
        bTangente = (Button) this.findViewById(R.id.buttonTangente);
        bMod = (Button) this.findViewById(R.id.buttonModule);
        setButton = (Button) this.findViewById(R.id.buttonGetOperador1);
        getButton = (Button) this.findViewById(R.id.buttonSetOperador1);
        calc = new Calculator();

        ImageView imageView = (ImageView)findViewById(R.id.imageAnim);
        imageView.setBackgroundResource(R.drawable.animation);
        animDraw = (AnimationDrawable)imageView.getBackground();

        ImageView imageView2 = (ImageView)findViewById(R.id.imageAnim2);
        imageView2.setBackgroundResource(R.drawable.animations_network);
        animDraw2 = (AnimationDrawable)imageView2.getBackground();

        slbIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                setContext(R.id.imageButtonSLB);
                return false;
            }
        });

        scpIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setContext(R.id.imageButtonSCP);
                return false;
            }
        });

        portoIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setContext(R.id.imageButtonPorto);
                return false;
            }
        });
        bSoma.setOnClickListener(this);
        bSub.setOnClickListener(this);
        bMult.setOnClickListener(this);
        bDiv.setOnClickListener(this);
        bCosseno.setOnClickListener(this);
        bSeno.setOnClickListener(this);
        bTangente.setOnClickListener(this);
        bMod.setOnClickListener(this);
        setButton.setOnClickListener(this);
        getButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        Calculator calc = new Calculator();
        TextView number1EditText = findViewById(R.id.editText);
        TextView number2EditText = findViewById(R.id.editText2);
        TextView number3EditText = findViewById(R.id.textResult);

        String sNum1 = number1EditText.getText().toString();
        String sNum2 = number2EditText.getText().toString();
        double num1 = getDouble(sNum1);
        double num2 = getDouble(sNum2);
        Button b = (Button) v;
        double value = 0;
        if (b.getId() == R.id.buttonSoma) {
            value = calc.sum(num1, num2);
        } else if (b.getId() == R.id.buttonSubtracao) {
            value = calc.sub(num1, num2);
        } else if (b.getId() == R.id.buttonMult) {
            value = calc.mult(num1, num2);
        } else if (b.getId() == R.id.buttonDiv) {
            value = calc.div(num1, num2);
        } else if (b.getId() == R.id.buttonSeno) {
            value = calc.seno(num1);
        } else if (b.getId() == R.id.buttonCos) {
            value = calc.cosseno(num1);
        } else if (b.getId() == R.id.buttonCos) {
            value = calc.tangente(num1);
        } else if (b.getId() == R.id.buttonModule) {
            value = calc.module(num1, num2);
        }else if (b.getId() == R.id.buttonSetOperador1) {
            setNum1(50);
        }else if (b.getId() == R.id.buttonGetOperador1) {
            getNum1();
        }
        number3EditText.setText(Double.toString(value));
    }

    private void setNum1(double newNum1){
        TextView number1EditText = findViewById(R.id.editText);
        String newNumber = Double.toString(newNum1);
        number1EditText.setText(newNumber);
        System.out.println("Yay apliquei o numero: " + newNum1 + " ao primeiro operador");
    }

    private double getDouble(String num) {
        return Double.parseDouble(num);
    }

    private double getNum1() {
        TextView number1EditText = findViewById(R.id.editText);
        String sNum1 = number1EditText.getText().toString();
        double num1 = getDouble(sNum1);
        System.out.println("Yay fui buscar o valor" + sNum1 + " do primeiro operador!");
        return num1;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animDraw.start();
        animDraw2.start();
    }


    private void setContext(int id) {
        TextView title;
        int fc = 0, bc = 0, textColor = 0;
        View container = (View) findViewById(R.id.container);
        if (id == R.id.imageButtonPorto) {
            fc = R.color.colorBlue;
            bc = R.color.colorBlue;
            textColor = R.color.colorWhite;

            container.setBackgroundResource(R.drawable.estadioporto);
            title = (TextView) findViewById(R.id.title);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.textViewOperand1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.textViewOperand3);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.textResult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.editText);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.editText2);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.buttonSoma);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSubtracao);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonMult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonDiv);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonCos);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSeno);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonTangente);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonModule);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonGetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

        } else if (id == R.id.imageButtonSLB) {
            fc = R.color.colorRed;
            bc = R.color.colorRed;
            textColor = R.color.colorWhite;

            container.setBackgroundResource(R.drawable.estadioluz);
            title = findViewById(R.id.title);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.textViewOperand1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.editText);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.textViewOperand3);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.editText2);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.textResult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSoma);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSubtracao);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonMult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonDiv);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonCos);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSeno);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonTangente);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonModule);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonGetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

        } else if (id == R.id.imageButtonSCP) {
            fc = R.color.colorGreen;
            bc = R.color.colorGreen;
            textColor = R.color.colorWhite;

            container.setBackgroundResource(R.drawable.estadioscp);
            title = (TextView) findViewById(R.id.title);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.editText);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.textViewOperand1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.editText2);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), textColor));
            title.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bc));

            title = (TextView) findViewById(R.id.textViewOperand3);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.textResult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSoma);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSubtracao);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonMult);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonDiv);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonCos);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSeno);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonTangente);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonModule);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonGetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));

            title = (TextView) findViewById(R.id.buttonSetOperador1);
            title.setTextColor(ContextCompat.getColor(getBaseContext(), fc));
        }
    }
}
