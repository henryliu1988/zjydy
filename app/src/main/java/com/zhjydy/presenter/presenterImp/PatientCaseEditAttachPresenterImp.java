package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.FileUpLoad;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.contract.PatientCaseEditAttachContract;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.zhview.ViewUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class PatientCaseEditAttachPresenterImp implements PatientCaseEditAttachContract.Presenter {

    private PatientCaseEditAttachContract.View mView;

    public PatientCaseEditAttachPresenterImp(PatientCaseEditAttachContract.View view) {
        mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    @Override
    public void uploadFiles(List<Map<String, Object>> files) {
        List<Map<String, Object>> existFile = new ArrayList<>();
        List<String> newFile = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            int imageType = Utils.toInteger(files.get(i).get(ViewKey.FILE_KEY_TYPE));
            if (imageType == ViewKey.TYPE_FILE_PATH) {
                String path = Utils.toString(files.get(i).get(ViewKey.FILE_KEY_URL));
                newFile.add(path);
            } else {
                existFile.add(files.get(i));
            }
        }
        Observable<List<Map<String, Object>>> obExist = Observable.just(existFile);
        if (newFile.size() > 0) {
            Observable.from(newFile).map(new Func1<String, File>() {
                @Override
                public File call(String s) {
                    return new File(s);
                }
            }).flatMap(new Func1<File, Observable<Map<String,Object>>>() {
                @Override
                public Observable<Map<String, Object>> call(File file) {
                    HashMap<String,Object> params = new HashMap<String, Object>();
                    params.put("X_FILENAME",".jpg");
                    return FileUpLoad.getInstance().uploadFile(file, params);
                }
            }).buffer(newFile.size()).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
                @Override
                public void onNext(List<Map<String, Object>> maps) {
                    int a = 0;
                }
            });
        } else {
        }
    }
}
