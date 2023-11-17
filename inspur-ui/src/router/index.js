import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      hidden: true,
      component: () => import('@/views/dashboard/index')
    }]
  },

  // 讲师管理
  {
    path: '/edu/teacher',
    component: Layout,
    redirect: '/edu/teacher/list',
    name: 'Teacher',
    meta: { title: '讲师管理', icon: 'peoples' },
    hidden: true,
    children: [
      {
        path: 'list',
        name: 'EduTeacherList',
        hidden: true,
        component: () => import('@/views/edu/teacher/list'),
        meta: { title: '讲师列表' }
      },
      {
        path: 'create',
        name: 'EduTeacherCreate',
        hidden: true,
        component: () => import('@/views/edu/teacher/form'),
        meta: { title: '添加讲师' }
      },
      {
        path: 'edit/:id',
        name: 'EduTeacherEdit',
        hidden: true,
        component: () => import('@/views/edu/teacher/form'),
        meta: { title: '编辑讲师', noCache: true },
      }
    ]
  },
  {
    path: '/edu/subject',
    component: Layout,
    redirect: '/edu/subject/list',
    hidden: true,
    name:'Subject',
    meta:{ title: '课程分类管理',icon: 'nested'},
    children: [
      {
        path: 'list',
        name: 'EduSubjectList',
        hidden: true,
        component:()=>import('@/views/edu/subject/list'),
        meta: {title: '课程分类列表'}
      },
      {
        path: 'import',
        name:'EduSubjectImport',
        hidden: true,
        component:()=>import('@/views/edu/subject/import'),
        meta: {title: '导入课程分类'}
      }
    ]
  },
  //课程管理
  {
    path:'/edu/course',
    component: Layout,
    redirect: '/edu/course/list',
    name:'Course',
    hidden: true,
    meta: {title:'课程管理',icon: 'form'},
    children: [
      {
        path: 'list',
        name: 'EduCourseList',
        hidden: true,
        component:()=>import('@/views/edu/course/list'),
        meta: {title: '课程列表'}
      },
      {
        path: 'info',
        name: 'EduCourseInfo',
        hidden: true,
        component:()=>import('@/views/edu/course/info'),
        meta: {title: '发布课程'}
      },
      {
        path: 'info/:id',
        name: 'EduCourseInfoEdit',
        component:()=>import('@/views/edu/course/info'),
        meta: {title: '编辑课程',noCache: true},
        hidden: true
      },
      {
        path: 'chapter/:id',
        name: 'EduCourseChapterEdit',
        component:()=>import('@/views/edu/course/chapter'),
        meta: {title: '编辑课程大纲',noCache: true},
        hidden: true
      },
      {
        path: 'publish/:id',
        name: 'EduCoursePublishEdit',
        component:()=>import('@/views/edu/course/publish'),
        meta: {title: '发布课程',noCache: true},
        hidden: true

      }
    ]
  },
  {
    path:'/vod/video',
    component: Layout,
    redirect: '/vod/video/upload',
    name:'video',
    hidden: true,
    meta: {title:'视频点播',icon: 'form'},
    children: [
      {
        path:'upload',
        name:'upload',
        hidden: true,
        component:()=>import('@/views/vod/upload'),
        meta: {title: '视频上传'}
      }
    ]
  },
  {
    path:'/cms/banner',
    component: Layout,
    name: 'cms',
    hidden: true,
    meta: {title: 'cms',icon:'el-icon-s-tools'},
    children: [
      {
        path: 'list',
        name: 'list',
        hidden: true,
        component:()=>import('@/views/edu/banner/list'),
        meta: {title: 'banner'}
      }
    ]
  },
  {
    path: '/statistics/daily',
    component:Layout,
    redirect: '/statistics/daily/create',
    name:'Statistics',
    hidden: true,
    meta: {title: '统计分析',icon:'example'},
    children: [
      {
        path: 'create',
        name: 'StatisticsDailyCreate',
        hidden: true,
        component: () => import('@/views/statistics/daily/create'),
        meta: { title: '生成统计',icon:'example' }
      },
      {
        path: 'chart',
        name: 'StatisticsDayChart',
        hidden: true,
        component: () => import('@/views/statistics/daily/chart'),
        meta: { title: '统计图表',icon:'example' }
      }
    ]
  },
  {
    path: '/acl',
    component: Layout,
    redirect: '/acl/user/list',
    name: '权限管理',
    hidden: true,
    meta: { title: '权限管理', icon: 'chart' },
    children: [
      {
        path: 'user/list',
        name: '用户管理',
        hidden: true,
        component: () => import('@/views/acl/user/list'),
        meta: { title: '用户管理' }
      },
      {
        path: 'role/list',
        name: '角色管理',
        hidden: true,
        component: () => import('@/views/acl/role/list'),
        meta: { title: '角色管理' }
      },
      {
        path: 'role/form',
        name: '角色添加',
        hidden: true,
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色添加' }
      },
      {
        path: 'role/update/:id',
        name: '角色修改',
        hidden: true,
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色修改' }
      },
      {
        path: 'role/distribution/:id',
        name: '角色权限',
        hide: true,
        component: () => import('@/views/acl/role/roleForm'),
        meta: { title: '角色权限' },

      },
      {
        path: 'menu/list',
        name: '菜单管理',
        hidden: true,
        component: () => import('@/views/acl/menu/list'),
        meta: { title: '菜单管理' }
      },
      {
        path: 'user/add',
        name: '用户添加',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户添加' },
        hidden: true,
      },
      {
        path: 'user/update/:id',
        name: '用户修改',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户修改' },
        hidden: true,
      },
      {
        path: 'user/role/:id',
        name: '用户角色',
        hide: true,
        component: () => import('@/views/acl/user/roleForm'),
        meta: { title: '用户角色' },
        hidden: true,
      }

    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
const router = createRouter()
export default router
