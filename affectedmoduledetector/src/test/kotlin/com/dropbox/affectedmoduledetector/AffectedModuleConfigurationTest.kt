package com.dropbox.affectedmoduledetector

import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.File

@RunWith(JUnit4::class)
class AffectedModuleConfigurationTest {

    @get:Rule
    val tmpFolder = TemporaryFolder()

    private lateinit var config : AffectedModuleConfiguration

    @Before
    fun setup() {
        config = AffectedModuleConfiguration()
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN log folder THEN is null`() {
        // GIVEN
        // config

        // WHEN
        val logFolder = config.logFolder

        // THEN

        assertThat(logFolder).isNull()
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN log folder is set THEN log folder is set`() {
        // GIVEN
        val sample = "sammple"

        // WHEN
        config.logFolder = sample

        // THEN

        assertThat(config.logFolder).isEqualTo(sample)
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN log file name THEN is default`() {
        // GIVEN
        // config

        // WHEN
        val logFilename = config.logFilename

        // THEN
        assertThat(logFilename).isEqualTo("affected_module_detector.log")
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN log file name is set THEN log file name is updated`() {
        // GIVEN
        val sample = "sample"

        // WHEN
        config.logFilename = sample

        // THEN
        assertThat(config.logFilename).isEqualTo(sample)
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN base dir is default THEN base dir is null`() {
        // GIVEN
        // config

        // WHEN
        val baseDir = config.baseDir

        // THEN

        assertThat(baseDir).isNull()
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN base dir is set THEN new base dir is return`() {
        // GIVEN
        val sample = "sample"

        // WHEN
        config.baseDir = sample

        // THEN

        assertThat(config.baseDir).isEqualTo(sample)
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN base dir is not set and paths affecting module is THEN throws an exception`() {
        // GIVEN
        val sample = setOf("sample")

        // WHEN
        try {
            config.pathsAffectingAllModules = sample
        } catch (e: IllegalArgumentException) {
            // THEN
            assertThat(e.message).isEqualTo("baseDir must be set to use pathsAffectingAllModules")
            return
        }

        fail("Expected to catch an exception")
    }


    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN base dir is set and paths affecting module is set THEN succeeds`() {
        // GIVEN
        val sampleFileName = "sample.txt"
        config.baseDir = tmpFolder.root.absolutePath
        val sample = File(tmpFolder.root, sampleFileName)
        sample.createNewFile()

        // WHEN
        config.pathsAffectingAllModules = setOf(sampleFileName)

        // THEN
        assertThat(config.pathsAffectingAllModules).isEqualTo(setOf(sampleFileName))
    }

    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN base dir is set and paths affecting module invalid file THEN throws exception`() {
        // GIVEN
        val sampleFileName = "sample.txt"
        config.baseDir = tmpFolder.root.absolutePath

        // WHEN
        config.pathsAffectingAllModules = setOf(sampleFileName)

        // THEN
        try {
            config.pathsAffectingAllModules
        } catch (e: IllegalArgumentException) {
            // THEN
            assertThat(e.message).startsWith("Could not find expected path in pathsAffectingAllModules:")
            return
        }

        fail("Expected to catch an exception")
    }


    @Test
    fun `GIVEN AffectedModuleConfiguration WHEN companion object name is returned THEN affectedModuleDetector is returned`() {
        assertThat(AffectedModuleConfiguration.name).isEqualTo("affectedModuleDetector")
    }

}