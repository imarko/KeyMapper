package io.github.sds100.keymapper.activity

import io.github.sds100.keymapper.viewmodel.EditKeyMapViewModel

open class EditKeymapActivity : ConfigKeymapActivity() {

    companion object {
        const val EXTRA_KEYMAP_ID = "extra_id"
    }

    override val viewModel: EditKeyMapViewModel by lazy {
        val id = intent.getLongExtra(EXTRA_KEYMAP_ID, 0)
        EditKeyMapViewModel.Factory(id, application).create(EditKeyMapViewModel::class.java)
    }
}
