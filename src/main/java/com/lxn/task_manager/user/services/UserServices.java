package com.lxn.task_manager.user.services;

import com.lxn.task_manager.user.model.UserModel;

import java.util.List;

public interface UserServices {

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user model corresponding to the given ID
     */
    UserModel getUserById(Long id);

    /**
     * Retrieves all users.
     *
     * @return a list of all user models
     */
    List<UserModel> getAllUsers();

    /**
     * Creates a new user.
     *
     * @param userModel the user model to create
     * @return the created user model
     */
    UserModel createUser(UserModel userModel);

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param userModel the user model with updated information
     * @return the updated user model
     */
    UserModel updateUser(Long id, UserModel userModel);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    void deleteUser(Long id);
}