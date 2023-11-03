/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
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
package com.example.myandroidtest.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.AbsGroupEntity;
import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.IEntity;
import com.arialyy.aria.core.wrapper.ITaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.example.myandroidtest.R;
import com.example.myandroidtest.utils.FileUtil;

import java.math.BigDecimal;

/**
 * 统一的进度布局
 */
public class ProgressLayout extends RelativeLayout implements View.OnClickListener {
    private final String TAG = "ProgressLayout";
    private TextView speedOrState, fileName, leftTime, fileSize;
    private HorizontalProgressBarWithNumber pb;
    private ImageButton delBt;
    private Button handleBt;
    private OnProgressLayoutBtListener listener;
    private AbsEntity entity;
    private int currentState;
    private TextView tvPath;

    public interface OnProgressLayoutBtListener {
        /**
         * 处理创建操作
         */
        void create(View v, AbsEntity entity);

        /**
         * 处理任务暂停的操作
         */
        void stop(View v, AbsEntity entity);

        /**
         * 处理恢复任务的操作
         */
        void resume(View v, AbsEntity entity);

        /**
         * 处理任务删除的操作
         */
        void cancel(View v, AbsEntity entity);
    }

    public ProgressLayout(Context context) {
        this(context, null);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_progress_content, this, true);
        speedOrState = findViewById(R.id.speed_or_state);
        fileName = findViewById(R.id.file_name);
        leftTime = findViewById(R.id.left_time);
        fileSize = findViewById(R.id.file_size);
        tvPath = findViewById(R.id.tv_path);
        pb = findViewById(R.id.pb);
        delBt = findViewById(R.id.del_bt);
        handleBt = findViewById(R.id.handle_bt);
        delBt.setOnClickListener(this);
        handleBt.setOnClickListener(this);
    }

    public void setBtListener(OnProgressLayoutBtListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            ALog.e(TAG, "没有设置OnProgressLayoutBtListener");
            return;
        }
//        if (entity == null) {
//            ALog.d(TAG, "entity 为空，请设置信息");
//            return;
//        }
        if (v.getId() == R.id.del_bt) {
            listener.cancel(v, entity);
            initState();
        } else if (v.getId() == R.id.handle_bt) {
            if (entity == null) {
                listener.create(v, null);
            } else {
                handleTask(v);
            }
        }

    }

    private void initState() {
        fileName.setText("-");
        leftTime.setText("");
        speedOrState.setText("");
        fileSize.setText("-/-");
        pb.setProgress(0);
    }

    private void handleTask(View v) {
        switch (entity.getState()) {
            case IEntity.STATE_OTHER:
            case IEntity.STATE_FAIL:
            case IEntity.STATE_STOP:
                listener.resume(v, entity);
                break;
            case IEntity.STATE_COMPLETE:
            case IEntity.STATE_WAIT:
                if (entity.getId() != -1) {
                    listener.resume(v, entity);
                } else {
                    listener.create(v, entity);
                }
                break;
            case IEntity.STATE_PRE:
            case IEntity.STATE_POST_PRE:
            case IEntity.STATE_RUNNING:
                listener.stop(v, entity);
                break;
            default:
                listener.create(v, entity);
                break;
        }
    }

    /**
     * 设置任务
     *
     * @param entity
     */
    public void setInfo(AbsEntity entity) {
        this.entity = entity;
        this.currentState = entity.getState();
        tvPath.setText("文件目录：" + ((DownloadEntity) entity).getFilePath());
        if (entity instanceof AbsNormalEntity) {
            AbsNormalEntity normalEntity = (AbsNormalEntity) entity;
            //ALog.d(TAG, "fileName = " + ((AbsNormalEntity) entity).getFileName());
            fileName.setText(normalEntity.getFileName());
        } else if (entity instanceof AbsGroupEntity) {
            AbsGroupEntity groupEntity = (AbsGroupEntity) entity;
            fileName.setText(
                    groupEntity.getAlias() == null ? groupEntity.getKey() : groupEntity.getAlias());
        }
        //如果是m3u8，隐藏总大小
        if (entity.getTaskType() == ITaskWrapper.M3U8_VOD || entity.getTaskType() == ITaskWrapper.M3U8_LIVE) {
            String str = CommonUtil.formatFileSize(entity.getCurrentProgress());
            fileSize.setText(str);
        } else {
            String str = CommonUtil.formatFileSize(entity.getCurrentProgress()) + "/" + CommonUtil.formatFileSize(entity.getFileSize());
            fileSize.setText(str);
        }

        leftTime.setText(CommonUtil.formatTime(entity.getTimeLeft()));
        String btStr = getResources().getString(R.string.start);
        String stateStr = "";
        switch (entity.getState()) {
            case IEntity.STATE_WAIT:
                btStr = getResources().getString(R.string.start);
                stateStr = getResources().getString(R.string.waiting);
                break;
            case IEntity.STATE_OTHER:
            case IEntity.STATE_FAIL:
                btStr = getResources().getString(R.string.start);
                stateStr = getResources().getString(R.string.state_error);
                break;
            case IEntity.STATE_STOP:
                btStr = getResources().getString(R.string.resume);
                stateStr = getResources().getString(R.string.stopped);
                break;
            case IEntity.STATE_PRE:
            case IEntity.STATE_POST_PRE:
            case IEntity.STATE_RUNNING:
                btStr = getResources().getString(R.string.stop);
                stateStr = entity.getConvertSpeed();
                leftTime.setText(CommonUtil.formatTime(entity.getTimeLeft()));
                break;
            case IEntity.STATE_COMPLETE:
                btStr = getResources().getString(R.string.re_start);
                stateStr = getResources().getString(R.string.completed);
                break;
            case IEntity.STATE_CANCEL:
                initState();
                break;
            default:
                btStr = getResources().getString(R.string.start);
                stateStr = "";
                leftTime.setText("");
        }
        this.handleBt.setText(btStr);
        this.speedOrState.setText(stateStr);
        if (entity.getState() != IEntity.STATE_CANCEL) {
            this.pb.setProgress(entity.getPercent());
        }
    }

    public void setFileName(Character fileName) {
        this.fileName.setText(fileName);
    }

    public void setLeftTime(Character leftTime) {
        this.leftTime.setText(leftTime);
    }

    public void setFileSize(Character fileSize) {
        this.fileSize.setText(fileSize);
    }

    public void setProgress(int progress) {
        this.pb.setProgress(progress);
    }

    public void setSpeed(Character speed) {
        this.speedOrState.setText(speed);
    }

    public void setState(Character state) {
        this.speedOrState.setText(state);
    }


}
