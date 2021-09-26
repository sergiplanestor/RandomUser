package com.splanes.presentation.component.finder

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.chip.Chip
import com.splanes.presentation.common.util.viewbinding.inflateViewBinding
import com.splanes.presentation.databinding.ComponentFinderViewBinding

class FinderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class QueryType {
        NAME,
        SURNAME,
        EMAIL
    }

    private val binding = inflateViewBinding(ComponentFinderViewBinding::inflate)
    private val chips: List<Chip> by lazy {
        listOf(
            binding.finderQueryTypeName,
            binding.finderQueryTypeSurname,
            binding.finderQueryTypeEmail
        )
    }

    private val queryText: String get() = binding.finderText.text.textOrEmpty()
    private var queryTypes: MutableList<QueryType> =
        mutableListOf(QueryType.NAME, QueryType.SURNAME, QueryType.EMAIL)
        set(value) {
            field = value
            onSubmitQuery?.invoke(queryText, value)
        }
    private var onSubmitQuery: ((String, List<QueryType>) -> Unit)? = null
    private var onClearQuery: (() -> Unit)? = null

    init {
        setupListeners()
    }

    fun bind(onSubmitQuery: (String, List<QueryType>) -> Unit, onClearQuery: () -> Unit) {
        this.onSubmitQuery = onSubmitQuery
        this.onClearQuery = onClearQuery
    }

    private fun setupListeners() {
        binding.finderText.doAfterTextChanged {
            val query = it.textOrEmpty()
            if (query.isBlank()) {
                onClearQuery?.invoke()
            } else {
                onSubmitQuery?.invoke(query, queryTypes)
            }
        }
        binding.finderText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSubmitQuery?.invoke(queryText, queryTypes)
                true
            } else {
                false
            }
        }
        chips.forEach { chip -> chip.setOnCheckedChangeListener { _, _ -> updateQueryType() } }
    }

    private fun updateQueryType() {
        queryTypes = binding.finderQueryTypeGroup.checkedChipIds.mapNotNull {
            it.mapToQueryTypeById()
        }.toMutableList()
    }

    private fun Int.mapToQueryTypeById(): QueryType? =
        when (this) {
            binding.finderQueryTypeName.id -> QueryType.NAME
            binding.finderQueryTypeSurname.id -> QueryType.SURNAME
            binding.finderQueryTypeEmail.id -> QueryType.EMAIL
            else -> null
        }

    private fun Editable?.textOrEmpty(): String =
        this?.toString().orEmpty()
}