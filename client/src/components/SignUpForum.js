import React, { useState, handleClick, useEffect } from "react";
import { useSelector } from "react-redux";
import EventNavbar from "./EventNavbar";
import "./SignUpForum.css";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import styled from "styled-components";
import axios from "axios";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";

const Button = styled.button`
  width: 100px;
  border: none;
  padding: 5px 10px;
  background-color: black;
  margin-top: 0px;
  color: white;
  cursor: pointer;
  margin-bottom: 5px;
  &:disabled {
    background-color: grey;
    cursor: not-allowed;
  }
`;

const Input = styled.textarea`
  width: 100%;
  height: 60%;
  margin: 10px 0;
`;

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  height: 200,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function SignUpForum() {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [openReply, setOpenReply] = React.useState(false);
  const handleOpenReply = () => setOpenReply(true);
  const handleCloseReply = () => setOpenReply(false);
  const [question, setQuestion] = useState("");
  const [questionId, setQuestionId] = useState("");
  const [response, setResponse] = useState("");
  const [signUpForum, setForum] = useState("");
  const [questionFormData, setQuestionFormData] = useState({
    forumId: "",
    userId: "",
    text: "",
    pictureUrl: "",
  });
  const [replyFormData, setReplyFormData] = useState({
    questionId: "",
    userId: "",
    text: "",
    pictureUrl: "",
  });

  const { currentUser } = useSelector((state) => state.user);

  useEffect(() => {
    axios
      .get(`/api/forum?eventId=${localStorage.getItem("eventId")}`)
      .then((res) => {
        if (res.status === 200) {
          setForum(res.data);
          setQuestionFormData({
            ...questionFormData,
            forumId: res.data.id,
            userId: currentUser.id,
          });
          console.log(res.data.questions);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, [open, openReply]);

  const ask = () => {
    console.log(question);
    axios
      .post(`/api/forum/question`, questionFormData)
      .then((res) => {
        if (res.status === 200) console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
    handleClose();
  };

  const reply = () => {
    console.log(response);
    axios
      .post(`/api/forum/answer`, replyFormData)
      .then((res) => {
        if (res.status === 200) console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
    handleCloseReply();
  };

  return (
    <div>
      <EventNavbar />
      {signUpForum ? (
        <div className="forum-container">
          <h1 className="forum-title">
            Sign Up Forum for {signUpForum.event.title}
          </h1>
          {signUpForum.event.status === "REGISTRATION_OPEN" ? (
            <button className="forum-buttons" onClick={handleOpen}>
              Ask a question!
            </button>
          ) : (
            <div></div>
          )}
          <Modal
            id="question-modal"
            open={open}
            onClose={handleClose}
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <Input
                autoFocus
                placeholder="Add your question here"
                onChange={(e) => {
                  setQuestion(e.target.value);
                  setQuestionFormData({
                    ...questionFormData,
                    text: e.target.value,
                  });
                }}
              />
              <button className="forum-buttons" onClick={ask}>
                Post Question
              </button>
            </Box>
          </Modal>
          <Modal
            id="reply-modal"
            open={openReply}
            onClose={handleCloseReply}
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <Input
                autoFocus
                placeholder="Add your reply here"
                onChange={(e) => {
                  setResponse(e.target.value);
                  setReplyFormData({
                    ...replyFormData,
                    questionId: questionId,
                    userId: currentUser.id,
                    text: e.target.value,
                  });
                }}
              />
              <button className="forum-buttons" onClick={reply}>
                Reply
              </button>
            </Box>
          </Modal>
          {signUpForum.questions.length > 0 ? (
            <div className="account-divs">
              <h5>Questions</h5>
              {signUpForum.questions.map((question) => {
                return (
                  <Card
                    key={question.id}
                    variant="outlined"
                    sx={{ width: "50%", margin: "15px" }}
                  >
                    <CardContent>
                      <Typography variant="h6" component="div">
                        {question.text}
                      </Typography>
                    </CardContent>
                    {signUpForum.event.status === "REGISTRATION_OPEN" ? (
                      <CardActions>
                        <Button
                          size="small"
                          onClick={() => {
                            handleOpenReply();
                            setQuestionId(question.id);
                          }}
                        >
                          Reply
                        </Button>
                      </CardActions>
                    ) : (
                      <div></div>
                    )}
                    {question.answers.length > 0 ? (
                      <CardContent>
                        <Typography variant="h5" component="div">
                          Replies
                        </Typography>
                        {question.answers.map((answer) => {
                          return (
                            <Card
                              key={answer.id}
                              variant="outlined"
                              sx={{ minWidth: 275 }}
                            >
                              <CardContent>
                                <Typography variant="caption" component="div">
                                  {answer.text}
                                </Typography>
                              </CardContent>
                            </Card>
                          );
                        })}
                      </CardContent>
                    ) : (
                      <div></div>
                    )}
                  </Card>
                );
              })}
            </div>
          ) : (
            <div></div>
          )}
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}

export default SignUpForum;
