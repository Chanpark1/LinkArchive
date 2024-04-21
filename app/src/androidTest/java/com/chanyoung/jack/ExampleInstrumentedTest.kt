package com.chanyoung.jack

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.chanyoung.jack.data.room.dao.LinkDao
import com.chanyoung.jack.data.room.database.JDatabase
import com.chanyoung.jack.ui.activity.GroupDetailActivity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.manipulation.Ordering.Context

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.chanyoung.jack", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {

    private lateinit var linkDao : LinkDao
    private lateinit var db : JDatabase


    @get:Rule
    val activityTestRule = ActivityTestRule(GroupDetailActivity::class.java)
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, JDatabase::class.java).allowMainThreadQueries().build()
        linkDao = db.getLinkDao()
    }
    @Test
    fun testRecyclerView() {

    }
}