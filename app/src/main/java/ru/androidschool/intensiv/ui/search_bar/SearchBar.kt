package ru.androidschool.intensiv.ui.search_bar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.SearchToolbarBinding
import ru.androidschool.intensiv.ui.afterTextChanged
import java.util.concurrent.TimeUnit

private const val EDIT_TEXT_DEBOUNCE = 300L
private const val EDIT_TEXT_MIN_LENGTH = 3

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val binding: SearchToolbarBinding by viewBinding(CreateMethod.INFLATE)

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    private val subject = PublishSubject.create<String>()

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    fun setText(text: String?) {
        binding.searchEditText.setText(text)
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    fun onTextChanged(): Observable<String> {
        return subject
            .debounce(EDIT_TEXT_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter { it.length > EDIT_TEXT_MIN_LENGTH }
            .distinctUntilChanged()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        with(binding) {
            searchEditText.hint = hint
            deleteTextButton.setOnClickListener { searchEditText.text.clear() }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        with(binding) {
            searchEditText.afterTextChanged { text ->
                subject.onNext(text.toString())
                if (!text.isNullOrEmpty() && !deleteTextButton.isVisible) {
                    deleteTextButton.visibility = View.VISIBLE
                }
                if (text.isNullOrEmpty() && deleteTextButton.isVisible) {
                    deleteTextButton.visibility = View.GONE
                }
            }
        }
    }
}
