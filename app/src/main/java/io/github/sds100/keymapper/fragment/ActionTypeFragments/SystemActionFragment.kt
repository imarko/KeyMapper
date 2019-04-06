package io.github.sds100.keymapper.fragment.ActionTypeFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.sds100.keymapper.*
import io.github.sds100.keymapper.adapter.SystemActionAdapter
import io.github.sds100.keymapper.interfaces.IContext
import io.github.sds100.keymapper.interfaces.OnItemClickListener
import io.github.sds100.keymapper.util.VolumeUtils
import io.github.sds100.keymapper.util.str
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

/**
 * Created by sds100 on 29/07/2018.
 */

/**
 * A Fragment which displays a list of all actions that can be performed on the system
 */
class SystemActionFragment : FilterableActionTypeFragment(),
        OnItemClickListener<SystemActionDef>, IContext {

    private val mSystemActionAdapter by lazy {
        SystemActionAdapter(
                iContext = this,
                onItemClickListener = this
        )
    }

    override val ctx: Context
        get() = context!!

    override val filterable: Filterable?
        get() = mSystemActionAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.adapter = mSystemActionAdapter
    }

    override fun onItemClick(item: SystemActionDef) {
        if (item.id == SystemAction.VOLUME_DECREASE_STREAM
                || item.id == SystemAction.VOLUME_INCREASE_STREAM) {

            VolumeUtils.showStreamPickerDialog(context!!) { streamType ->

                val action = Action(ActionType.SYSTEM_ACTION,
                        item.id,
                        Extra(Action.EXTRA_STREAM_TYPE, streamType.toString()))

                chooseSelectedAction(action)
            }

            return
        }

        if (item.messageOnSelection != null) {
            context?.alert {
                titleResource = item.descriptionRes
                messageResource = item.messageOnSelection
                okButton {  }
            }
        }

        val action = Action(ActionType.SYSTEM_ACTION, item.id)
        chooseSelectedAction(action)
    }
}