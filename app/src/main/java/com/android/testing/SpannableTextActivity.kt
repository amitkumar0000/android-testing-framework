package com.android.testing

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import kotlinx.android.synthetic.main.activity_spannable_text.*

class SpannableTextActivity : AppCompatActivity() {

    var isExpanded:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spannable_text)


        var strText = "To style text in Android, use spans!" +
                " Change the color of a few characters, make them clickable," +
                " scale the size of the text or even draw custom bullet points with spans. " +
                "Spans can change the TextPaint properties, draw on a Canvas, or " +
                "even change text layout and affect elements like the line height." +
                " Spans are markup objects that can be attached to and detached from text;" +
                " they can be applied to whole paragraphs or to parts of the text."

        var spannable:SpannableStringBuilder = SpannableStringBuilder(strText, 0, 40 + 1).append("....more");

        spannable.setSpan(ForegroundColorSpan(Color.RED),41,49,0)

        spannableText.text = spannable

        spannableText.setOnClickListener {
            if(!isExpanded){
                var expand:SpannableStringBuilder = SpannableStringBuilder(strText,0,strText.length).append("...less")
                expand.setSpan(ForegroundColorSpan(Color.RED),strText.length,strText.length+7,0)
                expand.setSpan(StyleSpan(Typeface.BOLD),strText.length,strText.length+7,0)
                expand.setSpan( RelativeSizeSpan(2f),strText.length,strText.length+7,0)
                spannableText.text = expand
                isExpanded = true
            }else{
                var collapse:SpannableStringBuilder = SpannableStringBuilder(strText, 0, 40 + 1).append("....more");
                collapse.setSpan(ForegroundColorSpan(Color.RED),41,49,0)
                collapse.setSpan(StyleSpan(Typeface.BOLD),41,49,0)
                collapse.setSpan(RelativeSizeSpan(2f),41,49,0)
                spannableText.text = collapse
                isExpanded = false
            }
        }
    }
}
