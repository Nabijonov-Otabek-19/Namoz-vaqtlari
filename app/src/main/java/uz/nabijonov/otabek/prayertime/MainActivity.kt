package uz.nabijonov.otabek.prayertime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import uz.nabijonov.otabek.prayertime.databinding.ActivityMainBinding
import uz.nabijonov.otabek.prayertime.presentation.screen.day.DayScreen
import uz.nabijonov.otabek.prayertime.presentation.screen.month.MonthScreen
import uz.nabijonov.otabek.prayertime.presentation.screen.week.WeekScreen

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val dayfragment = DayScreen.newInstance()
    private val weekfragment = WeekScreen.newInstance()
    private val monthfragment = MonthScreen.newInstance()
    private var activeFragment: Fragment = dayfragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, dayfragment, dayfragment.tag).hide(dayfragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, weekfragment, weekfragment.tag)
            .hide(weekfragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_Container, monthfragment, monthfragment.tag).hide(monthfragment).commit()


        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
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