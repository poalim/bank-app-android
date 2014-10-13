package com.bnhp.apiquizzer.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bnhp.apiquizzer.R;
import com.bnhp.apiquizzer.entities.Question;
import com.bnhp.apiquizzer.util.QuestionsHelper;
import com.bnhp.apiquizzer.util.ResolutionUtils;
import com.bnhp.apiquizzer.util.QuestionsHelper.OnHelperInitialized;
import com.bnhp.apiquizzer.util.QuestionsHelper.OnQuestionLoaded;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

public class QuestionsActivity extends Activity implements OnClickListener
{
	private static final int TRANSLATE_DP = 100;
	
	private Question question;
	private boolean isConfirmVisible = false;
	private float transPx;
	private List<TextView> listAnswersViews = new ArrayList<TextView>();
	
	private FloatingActionButton btnConfirm;
	private TextView txtQuestion;
	private TextView txtAnswer1;
	private TextView txtAnswer2;
	private TextView txtAnswer3;
	private TextView txtAnswer4;
	private RelativeLayout layoutQuestionsMain;
	private Button btnMore;
	private QuestionsHelper questionsHelper;
	private ProgressBar progressQuestions;
	private TextView txtFeedback;
	private TextView selectedAnswer = null;
	private OnQuestionLoaded onQuestionLoaded;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);
		
		btnConfirm = (FloatingActionButton) findViewById(R.id.btnConfirm);
		txtQuestion = (TextView) findViewById(R.id.txtQuestion);
		txtAnswer1 = (TextView) findViewById(R.id.txtAnswer1);
		txtAnswer2 = (TextView) findViewById(R.id.txtAnswer2);
		txtAnswer3 = (TextView) findViewById(R.id.txtAnswer3);
		txtAnswer4 = (TextView) findViewById(R.id.txtAnswer4);
		layoutQuestionsMain = (RelativeLayout) findViewById(R.id.layoutQuestionsMain);
		btnMore = (Button) findViewById(R.id.btnMore);
		progressQuestions = (ProgressBar) findViewById(R.id.progressQuestions);
		txtFeedback = (TextView) findViewById(R.id.txtFeedback);
		
		txtAnswer1.setOnClickListener(this);
		txtAnswer2.setOnClickListener(this);
		txtAnswer3.setOnClickListener(this);
		txtAnswer4.setOnClickListener(this);
		
		listAnswersViews.add(txtAnswer1);
		listAnswersViews.add(txtAnswer2);
		listAnswersViews.add(txtAnswer3);
		listAnswersViews.add(txtAnswer4);
		
		
		onQuestionLoaded = new OnQuestionLoaded()
		{
			@Override
			public void onLoaded(Question question)
			{
				QuestionsActivity.this.question = question;
				txtQuestion.setText(question.getQuestion());
				List<String> answers = question.getAnswers();
				txtAnswer1.setText(answers.get(0));
				txtAnswer2.setText(answers.get(1));
				txtAnswer3.setText(answers.get(2));
				txtAnswer4.setText(answers.get(3));
			}
		};
		
		transPx = ResolutionUtils.getPixels(TRANSLATE_DP, getResources());
		
		questionsHelper = QuestionsHelper.getInstance();
		questionsHelper.initialize(this, new OnHelperInitialized()
		{
			@Override
			public void onInitialized()
			{
				setQuestion();
				layoutQuestionsMain.setVisibility(View.VISIBLE);
				progressQuestions.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	private void setQuestion()
	{
		questionsHelper.generateQuestion(this, onQuestionLoaded);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onConfirmClick(View view)
	{
		setTextViewsClickable(false);
		
		btnConfirm.animate().translationXBy(-transPx).setDuration(100);
//		btnConfirm.animate().alpha(0).setDuration(200);
		setAnswerSelected(selectedAnswer, false);
		attemptAnswer();
	}
	
	private void showConfirmButton()
	{
		if((selectedAnswer == null && isConfirmVisible) || 
			selectedAnswer != null && !isConfirmVisible)
		{
			float trans = isConfirmVisible ? -transPx : transPx;
			
			btnConfirm.animate().translationXBy(trans).setDuration(100);
//			btnConfirm.animate().alpha(isConfirmVisible ? 0 : 100).setDuration(500);
			isConfirmVisible = !isConfirmVisible;
		}
	}

	@Override
	public void onClick(View v)
	{
		boolean isSameAnswerClicked = selectedAnswer != null && v.getId() == selectedAnswer.getId();
		
		if(selectedAnswer != null && isSameAnswerClicked)
		{
			setAnswerSelected((TextView)v, false);
			selectedAnswer = null;
		}
		else if(selectedAnswer != null)
		{
			setAnswerSelected(selectedAnswer, false);
			setAnswerSelected((TextView)v, true);
			selectedAnswer = (TextView) v;
		}
		else
		{
			setAnswerSelected((TextView)v, true);
			selectedAnswer = (TextView) v;
		}
		
		showConfirmButton();
	}
	
	private void setAnswerSelected(TextView answer, boolean selected)
	{
		answer.setTextColor(selected ? Color.WHITE : Color.BLACK);
		answer.setBackgroundResource(selected ? R.drawable.bg_card_red : R.drawable.bg_card);
	}
	
	private void attemptAnswer()
	{
		if(selectedAnswer.getId() == listAnswersViews.get(question.getCorrectAnswer()).getId())
		{
			// Show success
			txtFeedback.setText(questionsHelper.getSuccessString());
			txtFeedback.setTextColor(getResources().getColor(R.color.green));
		}
		else
		{
			// Show failure
			txtFeedback.setText(questionsHelper.getFailureString());
			txtFeedback.setTextColor(getResources().getColor(R.color.red));
		}
		
		txtFeedback.animate().alpha(100).setDuration(500);
		btnMore.animate().alpha(100).setDuration(1500);
	}
	
	public void onMoreClick(View v)
	{
		txtFeedback.animate().alpha(0).setDuration(100);
		btnMore.animate().alpha(0).setDuration(100);
//		btnConfirm.animate().alpha(100).setDuration(0);
//		btnConfirm.animate().translationXBy(-transPx).setDuration(0);
		isConfirmVisible = false;
		
		layoutQuestionsMain.animate().alpha(0).setDuration(500).withEndAction(new Runnable()
		{
			@Override
			public void run()
			{
				setQuestion();
				layoutQuestionsMain.animate().alpha(100).setDuration(1600);
				setTextViewsClickable(true);
			}
		});
	}
	
	private void setTextViewsClickable(boolean clickable)
	{
		txtAnswer1.setClickable(clickable);
		txtAnswer2.setClickable(clickable);
		txtAnswer3.setClickable(clickable);
		txtAnswer4.setClickable(clickable);
	}
}