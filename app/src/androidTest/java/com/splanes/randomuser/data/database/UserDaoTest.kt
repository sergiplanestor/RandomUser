package com.splanes.randomuser.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.splanes.data.feature.user.database.AppDatabase
import com.splanes.data.feature.user.database.dao.UserDao
import com.splanes.data.feature.user.database.entity.RemovedUserEntity
import com.splanes.data.feature.user.database.entity.UserEntity
import com.splanes.data.feature.user.mapper.UserMapper
import com.splanes.domain.feature.user.model.UserModel
import com.splanes.presentation.common.util.list.removeLast
import com.splanes.randomuser.randomUserSet
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class UserDaoTest {

    private lateinit var dao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun initialize() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    @Test
    fun insertAndFetch1User() {
        // Insert 1 user
        val user = randomUserSet(size = 1)
        val stored: List<UserModel> = runBlocking {
            dao.insertUsers(user.map(UserMapper::mapToUserEntity))
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(stored.firstOrNull()).isEqualTo(user.firstOrNull())
    }

    @Test
    fun insertAndFetch10Users() {
        // Insert 10 user
        val users = randomUserSet(size = 10)
        val stored = runBlocking {
            dao.insertUsers(users.map(UserMapper::mapToUserEntity))
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(stored).containsNoDuplicates()
        Truth.assertThat(stored).containsExactlyElementsIn(users)
    }

    @Test
    fun insertDuplicatedUsers() {
        // Insert 1 user
        val user = randomUserSet(size = 1)
        val stored = runBlocking {
            dao.insertUsers(user.map(UserMapper::mapToUserEntity))
            dao.insertUsers(user.map(UserMapper::mapToUserEntity))
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(stored).hasSize(1)
        Truth.assertThat(stored).containsNoDuplicates()
    }

    @Test
    fun insertEmptyUserList() {
        // Attempt to insert empty list when db is empty
        val emptyUserList = emptyList<UserEntity>()
        val storedWhenDBEmpty = runBlocking {
            dao.insertUsers(emptyUserList)
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(storedWhenDBEmpty).hasSize(0)

        val users = randomUserSet(size = 10)
        val storedWhenDBPopulated = runBlocking {
            dao.insertUsers(users.map(UserMapper::mapToUserEntity))
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(storedWhenDBPopulated).hasSize(10)

        val storedWhenAttemptToInsertEmptyList = runBlocking {
            dao.insertUsers(emptyUserList)
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }
        Truth.assertThat(storedWhenAttemptToInsertEmptyList).hasSize(10)
        Truth.assertThat(storedWhenAttemptToInsertEmptyList).containsNoDuplicates()
        Truth.assertThat(storedWhenAttemptToInsertEmptyList).containsExactlyElementsIn(storedWhenDBPopulated)
    }

    @Test
    fun insertAndFetch1RemovedUser() {
        // Insert 1 removed user
        val removedUser = RemovedUserEntity(randomUserSet(size = 1).first().id)
        val stored = runBlocking {
            dao.insertRemovedUser(removedUser)
            dao.fetchRemovedUsers()
        }
        Truth.assertThat(stored.firstOrNull()).isEqualTo(removedUser)
    }

    @Test
    fun insertDuplicatedRemovedUser() {
        // Insert 1 removed user
        val removedUser = RemovedUserEntity(randomUserSet(size = 1).first().id)
        val stored = runBlocking {
            dao.insertRemovedUser(removedUser)
            dao.insertRemovedUser(removedUser)
            dao.fetchRemovedUsers()
        }
        Truth.assertThat(stored).hasSize(1)
        Truth.assertThat(stored).containsNoDuplicates()
    }

    @Test
    fun deleteExistingUser() {
        // Insert 1 user
        val user = randomUserSet(size = 1)
        val storedAfterInsert: List<UserModel> = runBlocking {
            dao.insertUsers(user.map(UserMapper::mapToUserEntity))
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }

        Truth.assertThat(storedAfterInsert).hasSize(1)

        // Delete 1 user
        val storedAfterDelete: List<UserModel> = runBlocking {
            dao.deleteUser(user.map(UserMapper::mapToUserEntity).first())
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }

        Truth.assertThat(storedAfterDelete).hasSize(0)
    }

    @Test
    fun deleteNonExistingUser() {
        // Insert 1 user
        val user = randomUserSet(size = 2)
        val storedAfterInsert: List<UserModel> = runBlocking {
            dao.insertUsers(user.map(UserMapper::mapToUserEntity).removeLast())
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }

        Truth.assertThat(storedAfterInsert).hasSize(1)

        // Delete 1 user
        val storedAfterDelete: List<UserModel> = runBlocking {
            dao.deleteUser(user.map(UserMapper::mapToUserEntity)[1])
            dao.fetchUsers().map(UserMapper::mapToUserModel)
        }

        Truth.assertThat(storedAfterDelete).hasSize(1)
        Truth.assertThat(storedAfterDelete).containsExactlyElementsIn(storedAfterInsert)
    }

    @Test
    fun deleteExistingRemovedUser() {
        // Insert 1 removed user
        val removedUser = RemovedUserEntity(randomUserSet(size = 1).first().id)
        val storedAfterInsert = runBlocking {
            dao.insertRemovedUser(removedUser)
            dao.fetchRemovedUsers()
        }

        Truth.assertThat(storedAfterInsert).hasSize(1)

        // Delete 1 removed user
        val storedAfterDelete: List<RemovedUserEntity> = runBlocking {
            dao.deleteRemovedUser(removedUser)
            dao.fetchRemovedUsers()
        }

        Truth.assertThat(storedAfterDelete).hasSize(0)
    }

    @Test
    fun deleteNonExistingRemovedUser() {
        // Insert 1 removed user
        val users = randomUserSet(size = 2).map { RemovedUserEntity(it.id) }
        val storedAfterInsert = runBlocking {
            dao.insertRemovedUser(users[0])
            dao.fetchRemovedUsers()
        }

        Truth.assertThat(storedAfterInsert).hasSize(1)

        // Delete 1 removed user
        val storedAfterDelete: List<RemovedUserEntity> = runBlocking {
            dao.deleteRemovedUser(users[1])
            dao.fetchRemovedUsers()
        }

        Truth.assertThat(storedAfterDelete).hasSize(1)
        Truth.assertThat(storedAfterDelete).containsExactlyElementsIn(storedAfterInsert)
    }

    @After
    fun closure() {
        db.close()
    }
}