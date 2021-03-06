/*
 * Copyright (C) 2018 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jrooms.info_movies.screen.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPagination(
  val recyclerView: RecyclerView,
  private val isLoading: () -> Boolean,
  private val isRefreshing: () -> Boolean,
  private val onLoad: (Int, Boolean) -> Unit
) : RecyclerView.OnScrollListener() {

  var currentPage = 1

  init {
    recyclerView.addOnScrollListener(this)
    onLoad(currentPage,isRefreshing())
  }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)

    val layoutManager = recyclerView.layoutManager
    layoutManager?.let {
      val totalItemCount = it.itemCount
      val firstVisibleItemPosition = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
        is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
        else -> return
      }

      if (isLoading()) return

      if ((firstVisibleItemPosition + 1) == totalItemCount) {
        if (isRefreshing()){
          currentPage = 1
          onLoad(currentPage, isRefreshing())
        } else {
          onLoad(++currentPage, isRefreshing())
        }
      }
    }
  }
}