/*
 * Copyright 2018 F1ReKing. https://github.com/f1reking
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.f1reking.adapter;

/**
 * 描述：多条目接口
 *
 * @author F1ReKing
 * @time 2018/5/2
 */
public interface SuperMultiSupport<T> {

    /**
     * 获取多条目view类型的数量
     *
     * @return
     */
    int getViewTypeCount();

    /**
     * 根据数据，获取多条目布局
     *
     * @param data
     * @return
     */
    int getLayoutId(T data);

    /**
     * 根据数据，获取多条目的ItemViewType
     *
     * @param data
     * @return
     */
    int getItemViewType(T data);

    /**
     * 是否合并条目
     *
     * @param data
     * @return
     */
    boolean isSpan(T data);
}
