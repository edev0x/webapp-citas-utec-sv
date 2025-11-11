<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateUtils" uri="http://utec.edu/jsp/tlds/dateUtils" %>

<t:layout isPublic="false" isError="false" isLogin="false" showSidebar="true">
  <div class="rounded-2xl border border-gray-200 bg-white p-5 dark:border-gray-700 dark:bg-gray-800 md:p-6">
    <div id="calendar" class="min-h-screen w-full"></div>
  </div>
</t:layout>