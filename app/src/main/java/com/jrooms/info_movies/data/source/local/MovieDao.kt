/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jrooms.info_movies.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jrooms.info_movies.data.model.Movie

@Dao
interface MovieDao {

  @Query("SELECT * FROM Movie")
  fun observeMovies(): LiveData<List<Movie>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveMovies(movieInfoList: List<Movie>)

  @Query("SELECT * FROM Movie WHERE page = :page ")
  suspend fun getMovies(page: Int): List<Movie>

  @Query("SELECT * FROM Movie WHERE id= :id")
  suspend fun getMovie(id: Int): Movie

  @Query("DELETE FROM Movie")
  suspend fun deleteMovies(): Int
}