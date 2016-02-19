from rest_framework import permissions

#This is a pretty basic permission. If there is a user associated with the current request,
# we check whether that user is the same object as account. If there is no user associated with this request, we simply return False.

class IsAccountOwner(permissions.BasePermission):
    def has_object_permission(self, request, view, account):
        if request.user:
            return account == request.user
        return False