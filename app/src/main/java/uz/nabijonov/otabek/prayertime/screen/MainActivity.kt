package uz.nabijonov.otabek.prayertime.screen


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import uz.nabijonov.otabek.prayertime.R

class MainActivity : AppCompatActivity() {
    private val dayfragment = DayFragment.newInstance()
    private val weekfragment = WeekFragment.newInstance()
    private val monthfragment = MonthFragment.newInstance()
    private var activeFragment: Fragment = dayfragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, dayfragment, dayfragment.tag).hide(dayfragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, weekfragment, weekfragment.tag)
            .hide(weekfragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, monthfragment, monthfragment.tag).hide(monthfragment).commit()


        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionDay -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(dayfragment)
                        .commit()
                    activeFragment = dayfragment

                }
                R.id.actionWeek -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(weekfragment).commit()
                    activeFragment = weekfragment

                }
                R.id.actionMonth -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(monthfragment)
                        .commit()
                    activeFragment = monthfragment
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}