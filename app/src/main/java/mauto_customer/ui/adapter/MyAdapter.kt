package mauto_customer.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import mauto_customer.ui.mainpage.NewFragment
import mauto_customer.ui.mainpage.OldFragment

@Suppress("DEPRECATION")
internal class MyAdapter(
        var context: Context,
        fm: FragmentManager,
        var totalTabs: Int
) :
        FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                NewFragment()

            }
            1 -> {
                OldFragment()

            }

            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}