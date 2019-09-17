package com.fenjin.data;

import android.content.Context;

import com.fenjin.data.bean.ChartStatisticsItem;
import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.data.entity.BalanceQueryResult;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.EditPersonalInfoResult;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.data.entity.GetChannelResult;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.GetChengZhongStatisticsResult;
import com.fenjin.data.entity.GetSysConfigResult;
import com.fenjin.data.entity.LoadCompanyNamesResult;
import com.fenjin.data.entity.LoadPersonInfoResult;
import com.fenjin.data.entity.LoadSiteNamesResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.QueryCompanyResult;
import com.fenjin.data.entity.StatisticQueryCountResult;
import com.fenjin.data.entity.StatisticQueryDetailListResult;
import com.fenjin.data.entity.StatisticQueryListResult;
import com.fenjin.data.entity.TodayCountResult;
import com.fenjin.data.entity.UploadHeadImgResult;
import com.fenjin.data.memory.MemoryRepository;
import com.fenjin.data.network.NetworkRepository;
import com.fenjin.data.preferences.PreferencesRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:32
 * Summary:
 */
public class DataRepository {


    private NetworkRepository networkRepository;

    private PreferencesRepository preferencesRepository;

    private MemoryRepository memoryRepository;

    public DataRepository(Context context) {
        init(context);
    }


    /**
     * 初始化
     */
    private void init(Context context){
        preferencesRepository = new PreferencesRepository(context);
        networkRepository = new NetworkRepository(preferencesRepository);
        memoryRepository = new MemoryRepository();
        memoryRepository.setPersonalInfo(preferencesRepository.getPersonalInfo());
    }

    public Observable<LoginResult> login(LoginParam loginParam){
        return networkRepository.login(loginParam);
    }

    public Observable<ChengZhongRecordListResult> getChengZhongRecordList(int pageNum, int pageSize, String searchKey){
        return networkRepository.getChengZhongRecordList(getAuthorization(), pageNum, pageSize, searchKey);
    }

    public Observable<ChengZhongRecordListResult> getChengZhongRecordList(Map<String, Object> param) {
        return networkRepository.getChengZhongRecordList(param);
    }

    public void saveUserNameAndPassword(Context context, String userName, String password) {
        preferencesRepository.saveUserName(userName);
        preferencesRepository.savePassword(password);
        preferencesRepository.createUserPreferences(context);
    }

    public String getToken(){
        return preferencesRepository.getToken();
    }

    public String getAuthorization() {
        return preferencesRepository.getAuthorization();
    }

    public String getUserName(){
        return preferencesRepository.getUserName();
    }

    public String getPassword(){
        return preferencesRepository.getPassword();
    }

    public void saveToken(String token){
        preferencesRepository.saveToken(token);
    }

    public String getSysLogoUrl() {
        return preferencesRepository.getSysLogoUrl();
    }

    public void setSysLogoUrl(String sysLogoUrl) {
        preferencesRepository.setSysLogoUrl(sysLogoUrl);
    }

    public String getSysName() {
        return preferencesRepository.getSysName();
    }

    public void setSysName(String sysName) {
        preferencesRepository.setSysName(sysName);
    }

    public String getSysQrImgUrl() {
        return preferencesRepository.getSysQrImgUrl();
    }

    public void setSysQrImgUrl(String sysQrImgUrl) {
        preferencesRepository.setSysQrImgUrl(sysQrImgUrl);
    }

    public String getIp() {
        return preferencesRepository.getIp();
    }

    public String getPort() {
        return preferencesRepository.getPort();
    }

    public void setIpAndPort(String ip, String port) {
        preferencesRepository.setIp(ip);
        preferencesRepository.setPort(port);
        networkRepository.init();
    }

    public void setRememberPassword(boolean rememberPassword) {
        preferencesRepository.setRememberPassword(rememberPassword);
    }

    public String[] getSiteNames() {
        return preferencesRepository.getSandFactoryNames();
    }

    public void setSiteNames(List<String> names) {
        preferencesRepository.setSandFactoryNames(names);
    }

    public String[] getCompanyNames() {
        return preferencesRepository.getCompanyNames();
    }

    public void setCompanyNames(List<String> names) {
        preferencesRepository.setCompanyNames(names);
    }

    public boolean getRememberPassword() {
        return preferencesRepository.getRememberPassword();
    }

    public List<ChartStatisticsItem> getChartItemList() {
        return memoryRepository.getChartStatisticsItemList();
    }

    public void setChartItemList(List<ChartStatisticsItem> chartStatisticsItemList) {
        memoryRepository.setChartStatisticsItemList(chartStatisticsItemList);
    }

    public Observable<GetAllChannelResult> getAllChannel(){
        return networkRepository.getAllChannel();
    }

    public Observable<GetChannelResult> getChannel(int channel, String protocol){
        return networkRepository.getChannel(channel, protocol);
    }

    public Observable<GetChannelResult> touchChannel(int channel, String line, String protocol){
        return networkRepository.touchChannel(channel, line, protocol);
    }

    public Observable<ModifyPasswordResult> modifyPassword(ModifyPasswordParam modifyPasswordParam) {
        return networkRepository.modifyPassword(modifyPasswordParam);
    }


    public Observable<TodayCountResult> getTodayCountResult() {
        return networkRepository.getTodayCountResult();
    }

    public Observable<GetChengZhongStatisticsResult> getChengZhongStatic(Map<String, Object> param) {
        return networkRepository.getChengZhongStatic(param);
    }

    public Observable<GetSysConfigResult> getSysConfig() {
        return networkRepository.getSysConfig();
    }

    public Observable<GetChartStaticResult> getChartStatic() {
        return networkRepository.getChartStatic();
    }

    public Observable<LoadSiteNamesResult> loadSandFactoryNames() {
        return networkRepository.loadSandFactoryNames();
    }

    public Observable<LoadCompanyNamesResult> loadCompanyNames() {
        return networkRepository.loadCompanyNames();
    }

    public Observable<QueryCompanyResult> queryCompany(String keyword) {
        return networkRepository.queryCompany(keyword);
    }

    public Observable<LoadPersonInfoResult> loadPersonalInfo() {
        return networkRepository.loadPersonInfo();
    }

    public Observable<EditPersonalInfoResult> editPersonalInfo(PersonalInfo param) {
        return networkRepository.editPersonalInfo(param);
    }

    public Observable<UploadHeadImgResult> uploadHeadImg(MultipartBody.Part headImg) {
        return networkRepository.uploadHeadImg(headImg);

    }

    public Observable<StatisticQueryCountResult> getStatisticQueryCount(Map<String, Object> param) {
        return networkRepository.getStatisticQueryCount(param);
    }

    public Observable<StatisticQueryListResult> getStatisticQueryList(Map<String, Object> param) {
        return networkRepository.getStatisticQueryList(param);
    }

    public Observable<StatisticQueryDetailListResult> getStatisticQueryDetailList(Map<String, Object> param) {
        return networkRepository.getStatisticQueryDetailList(param);
    }

    public Observable<BalanceQueryResult> getBalanceQueryResult(Map<String, Object> param) {
        return networkRepository.getBalanceQueryResult(param);
    }

    public PersonalInfo getPersonalInfo() {
        return memoryRepository.getPersonalInfo();
    }

    public void savePersonalInfo(PersonalInfo info) {
        String url = "http://" + getIp() + ":" + getPort() + info.getHeadImg();
        info.setHeadImg(url);
        memoryRepository.setPersonalInfo(info);
        preferencesRepository.savePersonalInfo(info);
    }

    public List<String> getPackageNames() {
        return preferencesRepository.getPackageNames();
    }

    public void setPackageNames(List<String> names) {
        preferencesRepository.setPackageNames(names);
    }
}
