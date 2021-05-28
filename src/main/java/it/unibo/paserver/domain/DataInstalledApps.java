package it.unibo.paserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataInstalledApps", indexes = {
		@Index(name = "instapps_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "instapps_ts", columnNames = { "sampletimestamp" }) })
public class DataInstalledApps extends Data {

	private static final long serialVersionUID = 6986458996220319344L;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String pkgName;

	@NotNull
	private float versionCode;

	@NotNull
	private String versionName;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String requestedPermissions;

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public float getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(float versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getRequestedPermissions() {
		return requestedPermissions;
	}

	public void setRequestedPermissions(String requestedPermissions) {
		this.requestedPermissions = requestedPermissions;
	}

}
