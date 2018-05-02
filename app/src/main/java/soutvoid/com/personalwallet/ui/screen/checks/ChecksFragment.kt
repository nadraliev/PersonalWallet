package soutvoid.com.personalwallet.ui.screen.checks

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sembozdemir.permissionskt.askPermissions
import com.stfalcon.frescoimageviewer.ImageViewer
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.checks.list.ChecksListAdapter
import soutvoid.com.personalwallet.ui.screen.main.IToolbarAdapter
import soutvoid.com.personalwallet.ui.util.createCheckImageFile
import soutvoid.com.personalwallet.ui.util.getCheckImagesPaths
import java.io.File
import java.io.IOException

class ChecksFragment : BaseFragment(), ChecksView, IToolbarAdapter {

    companion object {
        fun newInstance(): ChecksFragment = ChecksFragment()
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    @BindView(R.id.checks_list)
    lateinit var checksList: RecyclerView
    private val checksAdapter = ChecksListAdapter()

    private var newImageFile: File? = null

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mChecksPresenter: ChecksPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): ChecksPresenter =
            ChecksPresenter(App.instance.kodein)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChecksList()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_checks

    override fun adaptToolbar(toolbar: Toolbar) {
        activity?.title = getString(R.string.checks_title)
    }

    @OnClick(R.id.checks_add_new_fab)
    fun addCheckClick(v: View) {
        mChecksPresenter.onAddCheck()
    }

    override fun takeNewPhoto() {
        activity?.askPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE) {
            onGranted {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(activity?.packageManager) != null) {
                    context?.let {
                        val photoFile = try {
                            it.createCheckImageFile()
                        } catch (e: IOException) {
                            null
                        }
                        photoFile ?: return@onGranted
                        newImageFile = photoFile
                        val photoUri = FileProvider.getUriForFile(it,
                                "com.soutvoid.fileprovider",
                                photoFile
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    override fun showChecks(checks: List<File>) {
        checksAdapter.photos.clear()
        checksAdapter.photos.addAll(checks)
        checksAdapter.notifyDataSetChanged()
    }

    override fun getAllChecks(callback: (List<File>) -> Unit) {
        context?.let {
            callback(it.getCheckImagesPaths())
        }
    }

    override fun openCheckFullscreen(file: File) {
        var pos = checksAdapter.photos.indexOfFirst { it == file }
        if (pos == -1) pos = 0
        context?.let {
            ImageViewer.Builder(context, checksAdapter.photos.map { file ->
                FileProvider.getUriForFile(it, "com.soutvoid.fileprovider", file)
            })
                    .setStartPosition(pos)
                    .show()
        }
    }

    private fun initChecksList() {
        checksList.adapter = checksAdapter
        checksAdapter.onItemClick = {
            mChecksPresenter.onCheckClick(checksAdapter.photos[it])
        }
        checksList.layoutManager = GridLayoutManager(context, 2)
    }
}