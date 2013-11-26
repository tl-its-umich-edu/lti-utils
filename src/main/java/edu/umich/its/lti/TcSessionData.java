package edu.umich.its.lti;

import java.util.UUID;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.umich.its.lti.utils.OauthCredentials;

/**
 *
 * @author ranaseef
 *
 */

/******** Use lombok for boilerplate code *********/
@Data
public class TcSessionData {
	// Instance variables -------------------------------------------

	private static Log M_log = LogFactory.getLog(TcSessionData.class);
	
	private final String id = UUID.randomUUID().toString();
	private String contextId;
	private String contextLabel;
	private String contextTitle;
	private String membershipsId;
	private String membershipsUrl;
	private String resourceLinkId;
	private String userEmailAddress;
	private String userId;
	private String userImageUrl;
	private String userNameFull;
	private String userRoles;
	private String settingId;
	private String settingUrl;
	private String setting;
 	private String locale;
	private String customBrand;
	private OauthCredentials oauthCredentials;


	// userRoleArray is generated by userRoles
	private String[] userRoleArray;
	private String userSourceDid;

	// Constructors -------------------------------------------------

	public TcSessionData(HttpServletRequest request) {
		loadTcParameters(request);
		M_log.debug("Tc value: "+this.toString());
	}


	// Public methods -----------------------------------------------

	/**
	 * Returns true if the user has the given role (match is case sensitive)
	 */
	public boolean getHasRole(String role) {
		String[] roleArray = getUserRoleArray();
		if (roleArray != null) {
			for (int idx = 0; idx < roleArray.length; idx++) {
				if (role.equals(roleArray[idx])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return true if the user has role "Instructor"
	 */
	public boolean getIsInstructor() {
		return getHasRole("Instructor");
	}

	/*
	 * basic-lti-loadsetting
	 * basic-lti-savesetting
	 * basic-lit-deletesetting
	 */

	public void loadTcParameters(HttpServletRequest request) {
		//setConsumerKey(request.getParameter("oauth_consumer_key"));
		setOauthCredentials(request.getParameter("oauth_consumer_key"));
		setContextId(request.getParameter("context_id"));
		setContextLabel(request.getParameter("context_label"));
		setContextTitle(request.getParameter("context_title"));
		
		setMembershipsId(request.getParameter("ext_ims_lis_memberships_id"));
		setMembershipsUrl(request.getParameter("ext_ims_lis_memberships_url"));
		
		setSettingId(request.getParameter("ext_ims_lti_tool_setting_id"));
		setSettingUrl(request.getParameter("ext_ims_lti_tool_setting_url"));
		setSetting(request.getParameter("ext_ims_lti_tool_setting"));
		
 		setLocale(request.getParameter("launch_presentation_locale"));
		setResourceLinkId(request.getParameter("resource_link_id"));
		setUserEmailAddress(request.getParameter("lis_person_contact_email_primary"));
		setUserId(request.getParameter("user_id"));
		setUserImageUrl(request.getParameter("user_image"));
		setUserNameFull(request.getParameter("list_person_name_full"));
		setUserRoles(request.getParameter("roles"));
		setUserSourceDid(request.getParameter("lis_person_sourcedid"));
		setCustomBrand(request.getParameter("custom_brand"));
	}

	public boolean matchTpId(String value) {
		return getId().equals(value);
	}

	public void setOauthCredentials(String consumerKey) {
		this.oauthCredentials = new OauthCredentials(consumerKey);
	}
	
	public void setUserRoles(String value) {
		userRoles = value;
		if (value != null) {
			setUserRoleArray(value.split(","));
		}
	}

	// Private methods ----------------------------------------------

	private void setUserRoleArray(String[] value) {
		userRoleArray = value;
	}

}
