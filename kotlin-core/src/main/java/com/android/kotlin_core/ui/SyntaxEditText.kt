package com.android.kotlin_core.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.kotlin_core.R
import com.android.kotlin_core.util.AppConst.SyntaxType
import com.android.kotlin_core.util.AppConst.SyntaxType.*
import com.android.kotlin_core.util.RegexUtil.*

/**
 * TODO: document your custom view class.
 */
class SyntaxEditText : ConstraintLayout, TextWatcher {

    var isSyntaxPass: Boolean = false
    private lateinit var tvInputTitle: TextView
    private lateinit var tvErrorMessage: TextView
    private lateinit var etInput: EditText
    private var errorMessage: String? = null
    private var inputBackgroundColor: Int = 0
    private var inputErrorBackgroundColor: Int = 0
    private var _syntaxType: SyntaxType = password

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val view = View.inflate(context, R.layout.sample_syntax_edit_text, this)
        tvInputTitle = view.findViewById(R.id.tv_input_title)
        tvErrorMessage= view.findViewById(R.id.tv_input_error_msg)
        etInput = view.findViewById(R.id.et_input)
        // Load attributes
        val attr = context.obtainStyledAttributes(attrs, R.styleable.SyntaxEditText, defStyle, 0)
        val value = attr.getInt(R.styleable.SyntaxEditText_syntaxType, 0)
        val inputTitle = attr.getString(R.styleable.SyntaxEditText_inputTitle)
        val inputTitleSize = attr.getDimensionPixelSize(R.styleable.SyntaxEditText_inputTitleSize, 0)
        val inputTitleColor = attr.getColor(R.styleable.SyntaxEditText_inputTitleColor, 0)

        errorMessage = attr.getString(R.styleable.SyntaxEditText_errorMessage)
        val errorMessageSize = attr.getDimensionPixelSize(R.styleable.SyntaxEditText_errorMessageSize, 0)
        val errorMessageColor = attr.getColor(R.styleable.SyntaxEditText_errorMessageColor, 0)

        val inputTextSize = attr.getDimensionPixelSize(R.styleable.SyntaxEditText_inputTextSize, 0)
        val inputTextColor = attr.getColor(R.styleable.SyntaxEditText_inputTextColor, 0)
        inputBackgroundColor = attr.getColor(R.styleable.SyntaxEditText_inputBackgroundColor, 0)
        inputErrorBackgroundColor = attr.getColor(R.styleable.SyntaxEditText_inputErrorBackgroundColor, 0)

        val inputHint = attr.getString(R.styleable.SyntaxEditText_inputHint)
        val inputHintColor = attr.getColor(R.styleable.SyntaxEditText_inputHintColor, 0)

        tvInputTitle.text = inputTitle
        tvInputTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputTitleSize.toFloat())
        tvInputTitle.setTextColor(inputTitleColor)

        tvErrorMessage.text = errorMessage
        tvErrorMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, errorMessageSize.toFloat())
        tvErrorMessage.setTextColor(errorMessageColor)

        etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputTextSize.toFloat())
        etInput.setTextColor(inputTextColor)
        etInput.setBackgroundColor(inputBackgroundColor)
        etInput.hint = inputHint
        etInput.setHintTextColor(inputHintColor)

        _syntaxType = SyntaxType.valueOf(SyntaxType.values()[value].name)
        etInput.addTextChangedListener(this)

        attr.recycle()
    }

    private fun checkSyntax(s: CharSequence) {
        if(s.trim().isEmpty()) {
            setInputErrorView()
            tvErrorMessage.text = "不可為空"
        } else {
            when(_syntaxType) {
                password -> checkSyntaxByType(checkPassword(s.toString()))
                email -> checkSyntaxByType(checkEmail(s.toString()))
                phone -> checkSyntaxByType(checkCellPhoneNumber(s.toString()))
            }
        }

    }

    private fun checkSyntaxByType(isSyntaxPass: Boolean) {
        this.isSyntaxPass = isSyntaxPass
        if (isSyntaxPass){
            setInputDefaultView()
        } else {
            setInputErrorView()
        }
    }

    private fun setInputDefaultView() {
        tvErrorMessage.visibility = GONE
        etInput.setBackgroundColor(inputBackgroundColor)
    }

    private fun setInputErrorView() {
        tvErrorMessage.visibility = VISIBLE
        tvErrorMessage.text = errorMessage
        etInput.setBackgroundColor(inputErrorBackgroundColor)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        checkSyntax(s)
    }

    override fun afterTextChanged(s: Editable) {
    }


}
